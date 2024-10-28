package com.codemages.cli.unit.mocks;

import com.codemages.cli.repository.exceptions.DmlException;
import com.codemages.cli.repository.exceptions.DqlException;
import com.codemages.cli.repository.interfaces.Repository;
import com.codemages.cli.tasks.entities.Task;
import com.codemages.cli.unit.mocks.interfaces.Mocker;

import java.util.List;

public class TaskRepositoryMock extends Mocker implements Repository<Task> {
	@Override
	public void insert(Task task) throws DmlException {
		saveMethodAndArgs("insert", new Object[]{task});
	}

	@Override public void update(Task object) throws DmlException {
		saveMethodAndArgs("update", new Object[]{object});
	}

	@Override
	public void delete(int id) throws DmlException {
		saveMethodAndArgs("delete", new Object[]{id});
	}

	@Override
	public Task getById(int id) throws DqlException {
		saveMethodAndArgs("getById", new Object[]{id});
		return new Task(
				id,
				"Task description",
				null,
				null,
				null
		);
	}

	@Override
	public List<Task> getAll() throws DqlException {
		saveMethodAndArgs("getAll", new Object[]{});

		return List.of(
				new Task(
						1,
						"Task description",
						null,
						null,
						null
				)
		);
	}
}
