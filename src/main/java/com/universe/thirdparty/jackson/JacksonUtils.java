package com.universe.thirdparty.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nick Liu
 * @date 2022/11/1
 */
public abstract class JacksonUtils {

	public static void main(String[] args) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "Nick");
		map.put("age", 26);
		map.put("gender", null);
		map.put("date", new Date());

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		ObjectWriter writer = objectMapper.writer();
		System.out.println(writer.writeValueAsString(map));
	}
}
