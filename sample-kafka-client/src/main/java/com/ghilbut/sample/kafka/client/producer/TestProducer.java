package com.ghilbut.sample.kafka.client.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
public class TestProducer {

	private static final int MAX_MESSAGE_COUNT = 100;
	private static final int WORKER_COUNT = 3;

	private Producer producer;
	private Queue<Integer> queue;
	private ExecutorService threadPool;
	private TestProduceWorker[] workers;

	public TestProducer(String topicName, Properties props) {
		this.producer = new KafkaProducer(props);
		this.queue = new LinkedBlockingQueue<>(MAX_MESSAGE_COUNT);
		this.threadPool = newFixedThreadPool(WORKER_COUNT);
		this.workers = new TestProduceWorker[WORKER_COUNT];

		for (int i = 0; i < WORKER_COUNT; ++i) {
			TestProduceWorker worker = new TestProduceWorker(i, topicName, producer, queue);
			threadPool.submit(worker);
			workers[i] = worker;
		}
	}

	public void send(int number) {
		queue.add(number);
	}

	public void close() {
		for (TestProduceWorker worker : workers) {
			worker.stop();
		}
		threadPool.shutdown();
		producer.close();
	}
}
