package com.codemages.cli.commands;

import com.codemages.cli.commands.exceptions.InvalidParameterException;
import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.tasks.services.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
	private final TaskService                    taskService;
	private final Map<String, Supplier<Command>> commandMap = new HashMap<>();

	public CommandFactory(TaskService taskService) {
		this.taskService = taskService;

		initializeCommandMap();
	}

	private void initializeCommandMap() {
		commandMap.put("add", () -> new AddCommand(taskService));
		commandMap.put("update", () -> new UpdateCommand(taskService));
		commandMap.put("delete", () -> new DeleteCommand(taskService));
		commandMap.put("list", () -> new ListCommand(taskService));
		commandMap.put(
				"mark-in-progress",
				() -> new MarkInProgressCommand(taskService)
		);
		commandMap.put("mark-done", () -> new MarkDoneCommand(taskService));
	}

	public Command getCommand(List<String> args)
			throws InvalidParameterException {

		if (args.isEmpty()) {
			throw new InvalidParameterException("Command is required");
		}

		String command = args.removeFirst();
		Supplier<Command> commandSupplier = commandMap.get(command);

		if (commandSupplier == null) {
			throw new InvalidParameterException("Invalid command: " + command);
		}

		return commandSupplier.get();
	}
}
