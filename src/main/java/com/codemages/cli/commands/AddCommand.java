package com.codemages.cli.commands;

import com.codemages.cli.commands.exceptions.InvalidParameterException;
import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.tasks.services.TaskService;

import java.util.List;

public class AddCommand implements Command {
	private final TaskService taskService;

	public AddCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	@Override
	public void execute(List<String> args) throws InvalidParameterException {
		if (args.isEmpty()) {
			throw new InvalidParameterException(
					"The Todo description is required");
		}

		String taskDescription = args.removeFirst();

		if (taskDescription.isEmpty()) {
			throw new InvalidParameterException(
					"The Todo description is required");
		}

		this.taskService.add(taskDescription);
	}
}
