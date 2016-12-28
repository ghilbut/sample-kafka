package com.ghilbut.sample.kafka.client.consumer;

import com.ghilbut.sample.kafka.client.common.TestDTO;
import com.ghilbut.sample.kafka.client.common.TestDTODeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
public class ConsumerTest {

	@Test
	public void test() {

		final Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.deserializer", IntegerDeserializer.class.getName());
		props.put("value.deserializer",TestDTODeserializer.class.getName());
		props.put("group.id", "sample1");

		for (int i = 0; i < 3; ++i) {

			new Thread(new Runnable() {

				private int num;

				@Override public void run() {
					Consumer<Integer, TestDTO> consumer = new KafkaConsumer<>(props);

					TopicPartition p = new TopicPartition("sample", num);
					consumer.assign(Arrays.asList(p));

					while (true) {
						ConsumerRecords<Integer, TestDTO> records = consumer.poll(100);
						for (ConsumerRecord<Integer, TestDTO> r : records) {
							log.info("[CONSUMING] T({}) P({}) V({})", num, r.partition(), r.value().getNumber());
						}
					}
				}

				public Runnable setPartition(int num) {
					this.num = num;
					return this;
				}
			}.setPartition(i)).start();
		}


		while (true) {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
