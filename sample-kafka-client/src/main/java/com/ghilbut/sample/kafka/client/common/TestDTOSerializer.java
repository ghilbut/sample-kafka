package com.ghilbut.sample.kafka.client.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
public class TestDTOSerializer implements Serializer<TestDTO> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override public byte[] serialize(String topic, TestDTO data) {
		if (data == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8);
		} catch (JsonProcessingException e) {
			log.error("[TestDTOSerializer] \n", e);
			return null;
		}
	}

	@Override public void close() {
		// nothing
	}
}
