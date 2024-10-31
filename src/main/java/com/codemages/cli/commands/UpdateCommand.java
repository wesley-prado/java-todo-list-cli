package com.codemages.cli.commands;

import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.tasks.services.TaskService;

import java.util.List;

public class UpdateCommand implements Command {
	private final TaskService taskService;

	public UpdateCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	public void execute(List<String> args) {
		if (args.size() < 2) {
			System.err.println("The task id and description are required");
			return;
		}

		String taskId = args.get(0);
		String description = args.get(1);

		try {
			int id = Integer.parseInt(taskId);

			taskService.update(id, description);
		} catch (NumberFormatException e) {
			System.err.println("Invalid task id: " + taskId);
		}
	}
}
