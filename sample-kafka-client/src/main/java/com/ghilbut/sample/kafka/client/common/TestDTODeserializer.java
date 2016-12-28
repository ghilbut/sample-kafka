package com.ghilbut.sample.kafka.client.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
public class TestDTODeserializer implements Deserializer<TestDTO> {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Override public void configure(Map<String, ?> configs, boolean isKey) {
		// nothing
	}

	@Override public TestDTO deserialize(String topic, byte[] data) {

		if (data == null) {
			return null;
		}

		try {
			return mapper.readValue(data, TestDTO.class);
		} catch (IOException e) {
			log.error("[TestDTODeserializer] \n", e);
			return null;
		}
	}

	@Override public void close() {
		// nothing
	}
}
