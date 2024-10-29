package com.codemages.cli.tasks.infra.fileHandlers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {
	private static final Pattern KEY_VALUE_PATTERN =
			Pattern.compile(
					"\"[^\"]+\"\\s*:\\s*(\"[^\"]*\"|\\d+|true|false|null)");

	public List<Map<String, Object>> parse(String json)
			throws IllegalArgumentException {
		List<Map<String, Object>> result = new ArrayList<>();

		if (json == null || json.isEmpty()) {
			return result;
		}

		char[] chars = json.trim().toCharArray();
		Set<Character> openers = new HashSet<>(Arrays.asList('{', '['));

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];

			if (c == '{') {
				StringBuilder buffer = new StringBuilder();
				while (i < chars.length) {
					buffer.append(chars[i]);

					int nextIndex = i + 1;
					if (nextIndex < chars.length &&
						openers.contains(chars[nextIndex])) {
						break;
					}

					i++;
				}

				String str = buffer.toString();

				if (str.contains("}")) {
					result.add(parseObject(str));
				} else {
					throw new IllegalArgumentException("Invalid JSON");
				}
			}
		}

		return result;
	}

	private Map<String, Object> parseObject(String json) {
		Map<String, Object> map = new HashMap<>();

		Matcher matcher = KEY_VALUE_PATTERN.matcher(json);

		while (matcher.find()) {
			String[] keyValue = matcher.group().split(":", 2);
			String key = keyValue[0].replaceAll("\"", "").trim();
			String value = keyValue[1].replaceAll("\"", "").trim();
			map.put(key, value);
		}

		return map;
	}

	public String toJSON(List<Map<String, Object>> jsonMap) {
		StringBuilder json = new StringBuilder("[\n");
		for (int i = 0; i < jsonMap.size(); i++) {
			Map<String, Object> map = jsonMap.get(i);
			json.append("\t{");

			Set<String> keySet = map.keySet();
			for (int x = 0; x < keySet.size(); x++) {
				String key = keySet.toArray(new String[0])[x];

				json.append("\n\t\t\"")
						.append(key)
						.append("\": ");
				Object value = map.get(key);

				if (value instanceof String) {
					json.append("\"")
							.append(value)
							.append("\"");
				} else {
					json.append(value);
				}

				if (x < keySet.size() - 1) json.append(",");
			}

			json.append("\n\t}");
			if (i < jsonMap.size() - 1) json.append(",\n");
		}
		json.append("\n]");
		return json.toString();
	}
}