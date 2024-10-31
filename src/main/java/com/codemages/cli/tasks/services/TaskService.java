package com.codemages.cli.tasks.services;

import com.codemages.cli.repository.exceptions.DmlException;
import com.codemages.cli.repository.interfaces.Repository;
import com.codemages.cli.tasks.entities.Task;
import com.codemages.cli.tasks.entities.enums.TaskStatus;

public class TaskService {
	private final Repository<Task> taskRepository;

	public TaskService(Repository<Task> taskRepository) {
		this.taskRepository = taskRepository;
	}

	public void add(String description) {

		Task task = new Task(
				description,
				TaskStatus.TODO
		);

		taskRepository.insert(task);

		System.out.println("Task added successfully. Id: " + task.getId());
	}

	public void listAll() {
		taskRepository.getAll().stream().forEach(System.out::println);
	}

	public void listByStatus(TaskStatus status) {
		taskRepository.getAll().stream()
				.filter(task -> task.getStatus().equals(status))
				.forEach(System.out::println);
	}

	public void markInProgress(int id) {
		changeStatus(id, TaskStatus.IN_PROGRESS);
	}

	public void markDone(int id) {
		changeStatus(id, TaskStatus.DONE);
	}

	public void delete(int id) {
		try {
			taskRepository.delete(id);
			System.out.println("Task deleted successfully. Id: " + id);
		} catch (DmlException e) {
			System.err.println("Error deleting task. Id: " + id);
		}
	}

	private void changeStatus(int id, TaskStatus status) {
		Task task = getTaskById(id);

		if (task == null) {
			System.err.println("Task not found. Id: " + id);
			return;
		}

		task.updateStatus(status);

		try {
			taskRepository.update(task);
			System.out.println("Task updated successfully. Id: " +
							   task.getId());
		} catch (DmlException e) {
			System.err.println("Error updating task. Id: " + task.getId());
		}
	}

	public void update(int id, String description) {
		Task task = getTaskById(id);

		if (task == null) {
			System.err.println("Task not found. Id: " + id);
			return;
		}

		task.updateDescription(description);

		try {
			taskRepository.update(task);
			System.out.println("Task updated successfully. Id: " +
							   task.getId());
		} catch (DmlException e) {
			System.err.println("Error updating task. Id: " + task.getId());
		}
	}

	private Task getTaskById(int id) {
		return taskRepository.getById(id);
	}

}
