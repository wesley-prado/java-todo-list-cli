package com.codemages.cli.unit.commands;

import com.codemages.cli.commands.AddCommand;
import com.codemages.cli.commands.exceptions.InvalidParameterException;
import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.repository.interfaces.Repository;
import com.codemages.cli.tasks.entities.Task;
import com.codemages.cli.tasks.entities.enums.TaskStatus;
import com.codemages.cli.tasks.services.TaskService;
import com.codemages.cli.unit.mocks.TaskRepositoryMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddCommandTest {
		private class SUTFactory {
				private final Command sut;
				private final Repository<Task> taskRepository;

				public SUTFactory() {
						this.taskRepository = new TaskRepositoryMock();
						this.sut = new AddCommand(new TaskService(taskRepository));
				}
		}

		@Test
		public void execute_shouldThrowException_whenNoArguments() {
				SUTFactory sutFactory = new SUTFactory();
				Command addCommand = sutFactory.sut;

				assertThrows(
								InvalidParameterException.class,
								() -> addCommand.execute(new ArrayList<>())
				);
		}

		@Test
		public void execute_shouldThrowException_whenEmptyArgument() {
				SUTFactory sutFactory = new SUTFactory();
				Command addCommand = sutFactory.sut;

				assertThrows(
								InvalidParameterException.class,
								() -> addCommand.execute(new ArrayList<>(List.of("")))
				);
		}

		@Test
		public void execute_shouldCallTaskRepositoryInsert_whenValidArgument() {
				SUTFactory sutFactory = new SUTFactory();
				Command addCommand = sutFactory.sut;

				try {
						addCommand.execute(new ArrayList<>(List.of("Task description")));
				} catch (InvalidParameterException e) {
						fail("Should not throw exception");
				}

				TaskRepositoryMock taskManager =
								(TaskRepositoryMock) sutFactory.taskRepository;
				taskManager.assertCalledWith(
								"insert",
								1,
								new Object[]{
												new Task(
																0,
																"Task description",
																TaskStatus.TODO,
																null,
																null
												)
								}
				);
		}
}
