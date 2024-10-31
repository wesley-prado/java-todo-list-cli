package com.codemages.cli.tasks.infra.fileHandlers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {
		private static final Pattern KEY_VALUE_PATTERN = Pattern.compile(
						"\"[^\"]+\"\\s*:\\s*(\"[^\"]*\"|\\d+|true|false|null)");

		public List<Map<String, Object>> parse(String json) {
				List<Map<String, Object>> result = new ArrayList<>();

				if (json == null || json.isEmpty()) {
						return result;
				}

				char[] chars = json.trim().toCharArray();
				for (int i = 0; i < chars.length; i++) {
						if (chars[i] == '{') {
								StringBuilder buffer = new StringBuilder();
								while (i < chars.length && chars[i] != '}') {
										buffer.append(chars[i++]);
								}
								buffer.append('}');
								result.add(parseObject(buffer.toString()));
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

						int x = 0;
						for (String key : map.keySet()) {
								json.append("\n\t\t\"").append(key).append("\": ");
								Object value = map.get(key);

								if (value instanceof String) {
										json.append("\"").append(value).append("\"");
								} else {
										json.append(value);
								}

								if (x++ < map.keySet().size() - 1) json.append(",");
						}

						json.append("\n\t}");
						if (i < jsonMap.size() - 1) json.append(",\n");
				}
				json.append("\n]");
				return json.toString();
		}
}