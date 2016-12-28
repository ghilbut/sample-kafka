package com.ghilbut.sample.kafka.client.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
public class TestProducer {

	private ExecutorService threadPool;
	private Producer producer;

	public TestProducer(Properties props) {
		this.producer = new KafkaProducer(props);
	}

	public void send(String topicName, int threadCount, int range, int loopCount) {
		threadPool = newFixedThreadPool(threadCount);
		for (int i = 0; i < threadCount; ++i) {
			threadPool.submit(new TestWorker(topicName, i, range, loopCount, producer));
		}
	}

	public void close() {
		producer.close();
	}
}
