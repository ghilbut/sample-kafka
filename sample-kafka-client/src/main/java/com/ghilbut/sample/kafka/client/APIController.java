package com.ghilbut.sample.kafka.client;

import com.ghilbut.sample.kafka.client.producer.TestProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(path="/test", method= RequestMethod.PUT)
	public void test() {
		producer.send("test", 3, 20, 100);
	}
}
