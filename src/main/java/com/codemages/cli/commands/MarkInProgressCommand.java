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
		System.out.println("Mark-in-progress command executed");
	}
}
