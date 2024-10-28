package com.codemages.cli.commands;

import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.tasks.services.TaskService;

import java.util.List;

public class ListCommand implements Command {
	private final TaskService taskService;

	public ListCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	public void execute(List<String> args) {
		taskService.list();
	}
}
