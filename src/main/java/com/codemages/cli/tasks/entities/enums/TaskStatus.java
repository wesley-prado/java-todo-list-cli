package com.codemages.cli.tasks.entities.enums;

import java.util.Arrays;

public enum TaskStatus {
	TODO, IN_PROGRESS, DONE;

	public static boolean isValid(String value) {
		return Arrays.stream(TaskStatus.values())
				.anyMatch(status -> status.name().equals(value));
	}
}
