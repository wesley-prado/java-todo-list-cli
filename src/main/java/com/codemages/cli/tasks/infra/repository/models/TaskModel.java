package com.codemages.cli.tasks.infra.repository.models;

import com.codemages.cli.tasks.entities.Task;
import com.codemages.cli.tasks.entities.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class TaskModel implements Comparable<TaskModel> {
	private int           id;
	private String        description;
	private String        status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public TaskModel() {
	}

	public TaskModel(
			int id,
			String description,
			String status,
			LocalDateTime createdAt,
			LocalDateTime updatedAt
	) {
		this.id = id;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatedAt(LocalDateTime now) {
		this.createdAt = now;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setUpdatedAt(LocalDateTime now) {
		this.updatedAt = now;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Task toTask() {
		return new Task(
				id,
				description,
				TaskStatus.valueOf(status),
				createdAt,
				updatedAt
		);
	}

	public Map<String, Object> toMap() {
		return Map.of(
				"id", id,
				"description", description,
				"status", status,
				"createdAt", createdAt.toString(),
				"updatedAt", updatedAt.toString()
		);
	}

	@Override public int compareTo(TaskModel o) {
		return Integer.compare(this.id, o.id);
	}
}
