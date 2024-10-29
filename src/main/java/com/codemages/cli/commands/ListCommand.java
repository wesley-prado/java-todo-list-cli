package com.codemages.cli.commands;

import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.tasks.entities.enums.TaskStatus;
import com.codemages.cli.tasks.services.TaskService;

import java.util.List;

public class ListCommand implements Command {
	private final TaskService taskService;

	public ListCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	public void execute(List<String> args) {
		if (args.isEmpty()) {
			taskService.list();
			return;
		}

		String status = args.getFirst();

		switch (status.toLowerCase()) {
			case "todo":
				taskService.listByStatus(TaskStatus.TODO);
				break;
			case "in-progress":
				taskService.listByStatus(TaskStatus.IN_PROGRESS);
				break;
			case "done":
				taskService.listByStatus(TaskStatus.DONE);
				break;
			default:
				System.err.println(
						"Invalid status. Use todo, in-progress or done");
		}
	}
}
