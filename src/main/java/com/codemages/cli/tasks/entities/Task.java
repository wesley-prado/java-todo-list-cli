package com.codemages.cli.tasks.entities;

import com.codemages.cli.tasks.entities.enums.TaskStatus;

import java.time.LocalDateTime;

public class Task implements Comparable<Task> {
		private int           id;
		private String        description;
		private TaskStatus    status;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public Task(String description, TaskStatus status) {
				this.description = description;
				this.status = status;
		}

		public Task(
						int id,
						String description,
						TaskStatus status,
						LocalDateTime createdAt,
						LocalDateTime updatedAt
		) {
				this.id = id;
				this.description = description;
				this.status = status;
				this.createdAt = createdAt;
				this.updatedAt = updatedAt;
		}

		public void setId(int id) {
				this.id = id;
		}

		public int getId() {
				return id;
		}

		public String getDescription() {
				return description;
		}

		public TaskStatus getStatus() {
				return status;
		}

		public LocalDateTime getUpdatedAt() {
				return updatedAt;
		}

		public void updateDescription(String description) {
				this.description = description;
				setUpdatedAtNow();
		}

		public void updateStatus(TaskStatus status) {
				this.status = status;
				setUpdatedAtNow();
		}

		private void setUpdatedAtNow() {
				this.updatedAt = LocalDateTime.now();
		}

		@Override
		public String toString() {
				return "Task{" +
							 "id=" + id +
							 ", description='" + description + '\'' +
							 ", status=" + status +
							 ", createdAt=" + createdAt +
							 ", updatedAt=" + updatedAt +
							 '}';
		}

		@Override
		public int compareTo(Task task) {
				return Integer.compare(id, task.getId());
		}
}
