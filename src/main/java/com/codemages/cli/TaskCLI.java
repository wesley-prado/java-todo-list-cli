package com.codemages.cli;

import com.codemages.cli.commands.CommandFactory;
import com.codemages.cli.commands.exceptions.InvalidParameterException;
import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.repository.interfaces.Repository;
import com.codemages.cli.tasks.entities.Task;
import com.codemages.cli.tasks.infra.repository.JsonTaskRepository;
import com.codemages.cli.tasks.services.TaskService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TaskCLI {
	private static final int SUCCESS = 0;
	private static final int FAILURE = 1;

	public Integer execute(String[] argsArray) {
		try {
			Repository<Task> taskRepository = new JsonTaskRepository();
			TaskService taskService = new TaskService(taskRepository);
			CommandFactory commandFactory = new CommandFactory(taskService);
			List<String> args = new ArrayList<>(Arrays.asList(argsArray));

			Command command = commandFactory.getCommand(args);
			command.execute(args);
		} catch (InvalidParameterException | IOException e) {
			System.err.println(e.getMessage());
			return FAILURE;
		}

		return SUCCESS;
	}
}