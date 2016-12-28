package com.ghilbut.sample.kafka.client;

import com.ghilbut.sample.kafka.client.common.TestDTODeserializer;
import com.ghilbut.sample.kafka.client.common.TestDTOSerializer;
import com.ghilbut.sample.kafka.client.consumer.TestConsumer;
import com.ghilbut.sample.kafka.client.producer.TestProducer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class SampleKafkaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleKafkaClientApplication.class, args);
	}

	@Bean
	public TestProducer producer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", IntegerSerializer.class.getName());
		props.put("value.serializer", TestDTOSerializer.class.getName());
		props.put("compression.type", "lz4");
		return new TestProducer(props);
	}

	@Bean
	public TestConsumer consumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.deserializer", IntegerDeserializer.class.getName());
		props.put("value.deserializer",TestDTODeserializer.class.getName());
		props.put("group.id", "test");
		return new TestConsumer(props);
	}
}
