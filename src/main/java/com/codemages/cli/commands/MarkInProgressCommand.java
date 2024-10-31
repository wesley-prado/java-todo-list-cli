package com.codemages.cli.commands;

import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.tasks.services.TaskService;

import java.util.List;

public class MarkInProgressCommand implements Command {
	private final TaskService taskService;

	public MarkInProgressCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	public void execute(List<String> args) {
		if (args.isEmpty()) {
			System.err.println("The task id is required");
			return;
		}

		String taskId = args.getFirst();

		try {
			int id = Integer.parseInt(taskId);
			taskService.markInProgress(id);
		} catch (NumberFormatException e) {
			System.err.println("Invalid task id: " + taskId);
		}
	}
}
