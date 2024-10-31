package com.codemages.cli.unit.commands;

import com.codemages.cli.commands.AddCommand;
import com.codemages.cli.commands.CommandFactory;
import com.codemages.cli.commands.interfaces.Command;
import com.codemages.cli.commands.exceptions.InvalidParameterException;
import com.codemages.cli.tasks.services.TaskService;
import com.codemages.cli.unit.mocks.TaskRepositoryMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandFactoryTest {
		private static class SUTFactory {
				private final CommandFactory sut;

				public SUTFactory() {
						sut =
										new CommandFactory(new TaskService(new TaskRepositoryMock()));
				}
		}

		@Test
		public void getCommand_WhenCommandIsAdd_ShouldReturnAddCommand() {
				SUTFactory sutFactory = new SUTFactory();
				CommandFactory cf = sutFactory.sut;

				try {
						Command command = cf.getCommand(new ArrayList<>(List.of("add")));

						Assertions.assertInstanceOf(
										AddCommand.class,
										command,
										"Command should be an instance of AddCommand"
						);
				} catch (InvalidParameterException e) {
						System.err.println(Arrays.toString(e.getStackTrace()));
						Assertions.fail("Exception thrown: " + e.getMessage());
				}
		}
}
