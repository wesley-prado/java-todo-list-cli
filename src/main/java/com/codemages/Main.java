package com.codemages;

import com.codemages.cli.TaskCLI;

public class Main {
	public static void main(String[] args) {
		int exitCode = new TaskCLI().execute(args);
		System.exit(exitCode);
	}
}