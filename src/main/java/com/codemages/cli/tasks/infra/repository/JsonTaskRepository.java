package com.codemages.cli.tasks.infra.repository;

import com.codemages.cli.repository.exceptions.DmlException;
import com.codemages.cli.repository.interfaces.Repository;
import com.codemages.cli.tasks.infra.fileHandlers.JsonFileHandler;
import com.codemages.cli.tasks.infra.fileHandlers.JsonParser;
import com.codemages.cli.tasks.infra.fileHandlers.JsonParserToDelete;
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
				.ifPresentOrElse(
						t -> {
							t.setDescription(task.getDescription());
							t.setStatus(task.getStatus().toString());
							t.setUpdatedAt(task.getUpdatedAt());
						},
						() -> {
							throw new DmlException("Task not found");
						}
				);

		saveTasks();
	}

	@Override
	public void delete(int id) {
		tasks.removeIf(t -> t.getId() == id);

		saveTasks();
	}

	@Override
	public Task getById(int id) {
		return tasks.stream().findFirst()
				.filter(t -> t.getId() == id)
				.map(TaskModel::toTask)
				.orElse(null);
	}

	@Override
	public List<Task> getAll() {
		return tasks.stream()
				.map(TaskModel::toTask)
				.toList();
	}

	protected final void loadTasks() {
		try {
			List<Map<String, Object>> jsonListMap =
					jsonParser.parse(jsonFileHandler.getFileContent());

			if (jsonListMap.isEmpty()) {
				return;
			}

			List<TaskModel> tasks = new ArrayList<>();

			for (Map<String, Object> map : jsonListMap) {
				System.out.println(map);
				tasks.add(new TaskModel(
						Integer.valueOf((String) map.get("id")),
						(String) map.get("description"),
						(String) map.get("status"),
						LocalDateTime.parse((String) map.get("createdAt")),
						LocalDateTime.parse((String) map.get("updatedAt"))
				));
			}

			this.tasks.addAll(tasks.stream()
									  .filter(task -> task.getId() != 0)
									  .sorted()
									  .toList());

			this.setStartingId(this.tasks.getLast().getId() + 1);

			this.tasks.addAll(tasks.stream()
									  .filter(task -> task.getId() == 0)
									  .peek(
											  task -> task.setId(this.generateId())
									  )
									  .toList());

			System.out.println(this.tasks.size() +
							   " Tasks loaded successfully");
			saveTasks();
		} catch (IOException e) {
			System.err.println("Error while loading tasks: " + e.getMessage());
		}
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