package com.codemages.cli.tasks.infra.repository.models;

import com.codemages.cli.tasks.entities.Task;
import com.codemages.cli.tasks.entities.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class TaskModel implements Comparable<TaskModel> {
		private static final LocalDateTime NOW         =
						LocalDateTime.now();
		private              int           id          = 0;
		private              String        description = "";
		private              String        status      =
						TaskStatus.TODO.name();
		private              LocalDateTime createdAt   = NOW;
		private              LocalDateTime updatedAt   = NOW;

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
				if (id > 0) {
						this.id = id;
				}
		}

		public String getDescription() {
				return description;
		}

		public void setDescription(String description) {
				if (description != null && !description.isEmpty()) {
						this.description = description;
				}
		}

		public String getStatus() {
				return status;
		}

		public void setStatus(String status) {
				if (TaskStatus.isValid(status)) {
						this.status = status;
				}
		}

		public void setCreatedAt(LocalDateTime createdAt) {
				if (createdAt != null) {
						this.createdAt = createdAt;
				}

				this.createdAt = createdAt;
		}

		public LocalDateTime getCreatedAt() {
				return createdAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
				if (updatedAt != null) {
						this.updatedAt = updatedAt;
				}
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
								"id",
								id,
								"description",
								description,
								"status",
								status,
								"createdAt",
								createdAt.toString(),
								"updatedAt",
								updatedAt.toString()
				);
		}

		@Override
		public int compareTo(TaskModel o) {
				return Integer.compare(this.id, o.id);
		}
}
