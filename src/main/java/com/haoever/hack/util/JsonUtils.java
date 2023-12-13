package com.vt.hacks.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private static ObjectMapper mapper = new ObjectMapper();
	//toJson
	public static String toJson(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}
	//toObject
	public static <T> T toObject(String json, Class<T> type) throws JsonProcessingException {
		return mapper.readValue(json, type);
	}
	//convert
	public static <T> T convert(Object data, Class<T> type) {
		return mapper.convertValue(data, type);
	}
}
