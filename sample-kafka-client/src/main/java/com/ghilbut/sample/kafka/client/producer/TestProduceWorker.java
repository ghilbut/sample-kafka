package com.ghilbut.sample.kafka.client.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghilbut.sample.kafka.client.common.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
public class TestProduceWorker implements Runnable {

	private static final ObjectMapper mapper = new ObjectMapper();

	private int id;
	private String topicName;
	private Producer<Integer, TestDTO> producer;
	private Queue<Integer> queue;

	private boolean isStop = false;

	public TestProduceWorker(int id, String topicName, Producer producer, Queue<Integer> queue) {
		this.id = id;
		this.topicName = topicName;
		this.producer = producer;
		this.queue = queue;
	}

	@Override public void run() {
		while (!isStop) {
			try {
				Integer number = queue.poll();
				if (number == null) {
					TimeUnit.MICROSECONDS.sleep(100);
					continue;
				}

				// produce to kafka
				TestDTO dto = new TestDTO(id, number);
				ProducerRecord<Integer, TestDTO> record =
					new ProducerRecord<>(topicName, number, dto);
				//Future<RecordMetadata> metadata = producer.send(record);
				producer.send(record);


				// notify to websocket
				//Map<String, Object> message = new HashMap<>();
				//message.put("type", producer);
				//message.put("data", dto);
				//String json = mapper.writeValueAsString(message);
				//WebSocketHandler.instance().send(json);

			} catch (InterruptedException e) {
				// nothing
			} catch(WakeupException e) {
				// nothing
			//} catch (JsonProcessingException e) {
				log.error("[TestProduceWorker] \n", e);
			//} catch (IOException e) {
				log.error("[TestProduceWorker] \n", e);
			}
		}
	}

	public void stop() {
		isStop = true;
	}
}
