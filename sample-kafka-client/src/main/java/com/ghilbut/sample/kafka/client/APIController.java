package com.ghilbut.sample.kafka.client;

import com.ghilbut.sample.kafka.client.producer.TestProducer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Coupang on 2016. 12. 28..
 */
@Slf4j
@RestController
@RequestMapping("/v1/api")
@CrossOrigin(origins="*")
public class APIController {

	@Autowired
	TestProducer producer;

	@RequestMapping(method= RequestMethod.GET)
	public @ResponseBody String sayHello() {
		return "Hello, World !!";
	}

	@RequestMapping(path="/produce", method= RequestMethod.PUT)
	public void produce(
		@RequestBody ProduceParam param) {

		for (int i = 0; i < param.count; ++i) {
			int number = ThreadLocalRandom.current().nextInt(0, param.range);
			producer.send(number);
		}
	}

	@RequestMapping(path="/consumer/start", method= RequestMethod.GET)
	public void start() {

	}

	@RequestMapping(path="/consumer/stop", method= RequestMethod.GET)
	public void stop() {

	}

	@Getter
	@NoArgsConstructor
	public static class ProduceParam {
		protected int count;
		protected int range;
	}
}
