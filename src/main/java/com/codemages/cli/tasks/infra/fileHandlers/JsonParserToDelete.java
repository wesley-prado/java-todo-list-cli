package com.codemages.cli.tasks.infra.fileHandlers;

import com.codemages.cli.tasks.entities.enums.TaskStatus;
import com.codemages.cli.tasks.infra.repository.models.TaskModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonParserToDelete {
	public List<TaskModel> parseTasks(BufferedReader reader)
			throws IOException {
		List<TaskModel> tasks = new ArrayList<>();
		String line;
		StringBuilder buffer = new StringBuilder();
		TaskModel task = null;

		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}

		new JsonParser().parse(buffer.toString());

//		while ((line = reader.readLine()) != null) {
//			String trimmedLine = line.trim();
//			boolean isBracketLine = trimmedLine.isEmpty() ||
//								trimmedLine.contains("[") ||
//								trimmedLine.contains("]");
//
//			if (isBracketLine) {continue;}
//
//			if (trimmedLine.contains("{")) {
//				task = new TaskModel();
//				continue;
//			}
//
//			if (trimmedLine.contains("}") && task != null) {
//				if (task.getDescription().isEmpty()) {
//					System.err.println("Warning: Task with Id \"" +
//									   task.getId() +
//									   "\" has empty description and will be" +
//									   " " +
//									   "ignored");
//					continue;
//				}
//
//				if (task.getCreatedAt() == null) {
//					System.err.println("Warning: Task with Id \"" +
//									   task.getId() +
//									   "\" has no created date. Setting to " +
//									   "current date");
//					task.setCreatedAt(LocalDateTime.now());
//				}
//
//				if (task.getUpdatedAt() == null) {
//					System.err.println("Warning: Task with Id \"" +
//									   task.getId() +
//									   "\" has no updated date. Setting to " +
//									   "current date");
//					task.setUpdatedAt(LocalDateTime.now());
//				}
//
//				tasks.add(task);
//				continue;
//			}
//
//			if (task == null) throw new IOException("Invalid JSON format");
//
//			JsonKeyValuePair pair = getKeyValuePair(trimmedLine);
//
//			setValueIntoTask(task, pair.key, pair.value);
//		}

		return tasks;
	}

	private JsonKeyValuePair getKeyValuePair(String trimmedLine)
			throws IOException {
		String[] parts = trimmedLine.replace(",", "").split(":", 2);
		if (parts.length != 2) {
			throw new IOException("Invalid JSON " +
								  "format");
		}

		return new JsonKeyValuePair(parts[0], parts[1]);
	}

	private record JsonKeyValuePair(String key, String value) {}

	private static void setValueIntoTask(
			TaskModel task,
			String key,
			String value
	) {
		try {
			switch (key) {
				case "id" -> task.setId(Integer.parseInt(value));
				case "description" -> task.setDescription(value);
				case "status" -> {
					if (Arrays.stream(TaskStatus.values())
							.anyMatch(status -> status.name()
									.equals(value))) {
						task.setStatus(value);
					} else {
						System.err.println("Warning: Unknown status: \"" +
										   value +
										   "\" for task with id: \"" +
										   task.getId() +
										   "\". Status will be set to " +
										   "TODO");
						task.setStatus(TaskStatus.TODO.toString());
					}
				}
				case "createdAt" -> task.setCreatedAt(LocalDateTime.parse(
						value,
						DateTimeFormatter.ISO_DATE_TIME
				));
				case "updatedAt" -> task.setUpdatedAt(LocalDateTime.parse(
						value,
						DateTimeFormatter.ISO_DATE_TIME
				));
				default -> System.err.println("Warning: Unknown key: \"" +
											  key +
											  "\" for task with id: \"" +
											  task.getId() +
											  "\". Ignoring");
			}
		} catch (DateTimeParseException e) {
			System.err.println("Warning: Invalid value for key: \"" +
							   key +
							   "\" for task with id: \"" +
							   task.getId() +
							   "\".");
		}
	}

	public String toJSON(List<TaskModel> tasks) {
		StringBuilder json = new StringBuilder("[\n");
		for (int i = 0; i < tasks.size(); i++) {
			TaskModel task = tasks.get(i);
			json.append("\t{")
					.append("\n\t\t\"id\": ")
					.append(task.getId())
					.append(",")
					.append("\n\t\t\"description\": \"")
					.append(task.getDescription())
					.append("\",")
					.append("\n\t\t\"status\": \"")
					.append(task.getStatus())
					.append("\",")
					.append("\n\t\t\"createdAt\": \"")
					.append(task.getCreatedAt())
					.append("\",")
					.append("\n\t\t\"updatedAt\": \"")
					.append(task.getUpdatedAt())
					.append("\"\n\t}");
			if (i < tasks.size() - 1) json.append(",\n");
		}
		json.append("\n]");
		return json.toString();
	}
}