package com.codemages.cli.tasks.infra.repository;

import com.codemages.cli.repository.exceptions.DmlException;
import com.codemages.cli.repository.interfaces.Repository;
import com.codemages.cli.tasks.entities.enums.TaskStatus;
import com.codemages.cli.tasks.infra.fileHandlers.JsonFileHandler;
import com.codemages.cli.tasks.infra.fileHandlers.JsonParser;
import com.codemages.cli.tasks.entities.Task;
import com.codemages.cli.tasks.infra.repository.models.TaskModel;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonTaskRepository implements Repository<Task> {
		protected final List<TaskModel> tasks      = new ArrayList<>();
		private         int             nextId     = 1;
		private final   JsonFileHandler jsonFileHandler;
		private final   JsonParser      jsonParser = new JsonParser();

		public JsonTaskRepository() throws IOException {
				jsonFileHandler = new JsonFileHandler("tasks.json");
				loadTasks();
		}

		public int generateId() {
				return nextId++;
		}

		public void setStartingId(int nextId) throws IllegalArgumentException {
				if (nextId < 1) {
						throw new IllegalArgumentException("Next ID must be greater than" +
																							 " " +
																							 "0");
				}

				this.nextId = nextId;
		}

		@Override
		public void insert(Task task) {
				int id = generateId();
				LocalDateTime now = LocalDateTime.now();
				tasks.add(new TaskModel(
								id,
								task.getDescription(),
								task.getStatus().toString(),
								now,
								now
				));

				saveTasks();

				task.setId(id);
		}

		@Override
		public void update(Task task) {
				assertTaskIsNotNull(task);

				tasks.stream()
								.filter(t -> t.getId() == task.getId())
								.findFirst()
								.ifPresentOrElse(t -> {
										t.setDescription(task.getDescription());
										t.setStatus(task.getStatus().toString());
										t.setUpdatedAt(task.getUpdatedAt());
								}, () -> {
										throw new DmlException("Task not found");
								});

				saveTasks();
		}

		@Override
		public void delete(int id) {
				tasks.removeIf(t -> t.getId() == id);

				saveTasks();
		}

		@Override
		public Task getById(int id) {
				return tasks.stream()
								.filter(t -> t.getId() == id)
								.map(TaskModel::toTask)
								.findFirst()
								.orElse(null);
		}

		@Override
		public List<Task> getAll() {
				return tasks.stream().map(TaskModel::toTask).toList();
		}

		protected final void loadTasks() {
				try {
						List<Map<String, Object>> jsonListMap =
										jsonParser.parse(jsonFileHandler.getFileContent());

						if (!jsonListMap.isEmpty()) {
								List<TaskModel> tasks = jsonListMap.stream()
												.map(this::getTaskModel)
												.toList();

								this.tasks.addAll(filterAndSortTasksById(tasks));
								this.tasks.addAll(assignIdsToTasksWithoutIds(tasks));

								System.out.println(this.tasks.size() +
																	 " Tasks loaded successfully");
						}
				} catch (IOException e) {
						System.err.println("Error while loading tasks: " + e.getMessage());
				}
		}

		private TaskModel getTaskModel(Map<String, Object> map) {
				TaskModel task = new TaskModel();

				if (map.get("id") instanceof String id &&
						((String) map.get("id")).matches("\\d+")) {
						task.setId(Integer.valueOf(id));
				}

				if (map.get("description") instanceof String description) {
						task.setDescription(description);
				}

				if (map.get("status") instanceof String status &&
						TaskStatus.isValid(status)) {
						task.setStatus(status);
				}

				if (map.get("createdAt") instanceof String createdAt &&
						!createdAt.isBlank()) {
						task.setCreatedAt(LocalDateTime.parse(createdAt));
				}

				if (map.get("updatedAt") instanceof String updatedAt &&
						!updatedAt.isBlank()) {
						task.setUpdatedAt(LocalDateTime.parse(updatedAt));
				}

				return task;
		}

		private List<TaskModel> assignIdsToTasksWithoutIds(List<TaskModel> tasks) {
				if (tasks.isEmpty()) {
						return List.of();
				}

				updateStartingIdBasedOnLastTask();

				return tasks.stream()
								.filter(task -> task.getId() == 0)
								.peek(task -> task.setId(this.generateId()))
								.toList();
		}

		private void updateStartingIdBasedOnLastTask() {
				if (!this.tasks.isEmpty()) {
						int nextId = this.tasks.getLast().getId() + 1;
						this.setStartingId(nextId);
				}
		}

		private List<TaskModel> filterAndSortTasksById(List<TaskModel> tasks) {
				return tasks.stream()
								.filter(task -> task.getId() != 0)
								.sorted()
								.toList();
		}

		protected final void saveTasks() {
				try {
						List<Map<String, Object>> jsonMap = new ArrayList<>();
						for (TaskModel task : tasks) {
								jsonMap.add(task.toMap());
						}

						jsonFileHandler.writeToFile(jsonParser.toJSON(jsonMap));
				} catch (IOException e) {
						System.err.println("Error while saving tasks: " + e.getMessage());
				}
		}

		private void assertTaskIsNotNull(Task task) throws DmlException {
				if (task == null) {
						throw new DmlException("Task is null");
				}
		}
}