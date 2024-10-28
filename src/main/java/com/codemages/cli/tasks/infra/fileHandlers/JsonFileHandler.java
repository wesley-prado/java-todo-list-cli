package com.codemages.cli.tasks.infra.fileHandlers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileHandler {
	private final File jsonFile;

	public JsonFileHandler(String fileName) throws IOException {
		jsonFile = new File(fileName);
		if (jsonFile.createNewFile()) {
			System.out.println("File created: " + jsonFile.getName());
		}
		if (!jsonFile.setWritable(true, true)) {
			throw new IOException("Cannot write to file");
		}
	}

	public String getFileContent() throws IOException {
		return new String(Files.readAllBytes(Paths.get(jsonFile.getPath())));
	}

	public FileWriter getWriter() throws IOException {
		return new FileWriter(jsonFile);
	}

	public void writeToFile(String content) throws IOException {
		try (FileWriter writer = getWriter()) {
			writer.write(content);
		}
	}
}