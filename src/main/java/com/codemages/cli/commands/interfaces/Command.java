package com.codemages.cli.commands.interfaces;

import com.codemages.cli.commands.exceptions.InvalidParameterException;

import java.util.List;

public interface Command {
	void execute(List<String> args) throws InvalidParameterException;
}
