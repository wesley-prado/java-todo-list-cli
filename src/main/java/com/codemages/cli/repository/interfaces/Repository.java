package com.codemages.cli.repository.interfaces;

import com.codemages.cli.repository.exceptions.DmlException;

import java.util.List;

public interface Repository<T> {
		void insert(T object) throws DmlException;

		void update(T object) throws DmlException;

		void delete(int id) throws DmlException;

		T getById(int id);

		List<T> getAll();
}
