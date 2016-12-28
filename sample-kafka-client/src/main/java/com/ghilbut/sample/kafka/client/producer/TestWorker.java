package com.ghilbut.sample.kafka.client.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghilbut.sample.kafka.client.WebSocketHandler;
import com.ghilbut.sample.kafka.client.common.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
public class TestWorker implements Runnable {

	private static final ObjectMapper mapper = new ObjectMapper();

	private String topicName;
	private int id;
	private int range;
	private int loopCount;
	private Producer<Integer, TestDTO> producer;

	public TestWorker(String topicName, int id, int range, int loopCount, Producer producer) {
		this.topicName = topicName;
		this.id = id;
		this.range = range;
		this.loopCount = loopCount;
		this.producer = producer;
	}

	@Override public void run() {
		for (int i = 0; i < loopCount; ++i) {
			try {
				int number = ThreadLocalRandom.current().nextInt(0, range);
				TestDTO dto = new TestDTO();
				ProducerRecord<Integer, TestDTO> record =
					new ProducerRecord<>(topicName, number, dto);
				//Future<RecordMetadata> metadata = producer.send(record);
				//log.info("[TestWorker] metadata: {}", metadata);

				Map<String, Object> message = new HashMap<>();
				message.put("type", producer);
				message.put("data", dto);
				String json = mapper.writeValueAsString(message);
				WebSocketHandler.instance().send(json);

				TimeUnit.MILLISECONDS.sleep(100);

			} catch (InterruptedException e) {
				// nothing
			} catch(WakeupException e) {
				// nothing
			} catch (JsonProcessingException e) {
				log.error("[TestWorker] \n", e);
			} catch (IOException e) {
				log.error("[TestWorker] \n", e);
			}
		}
	}
}
