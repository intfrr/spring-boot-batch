package com.spring.cloud.streamdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class StreamDemoApplication {
	private static final Logger LOG = LoggerFactory.getLogger(StreamDemoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(StreamDemoApplication.class, args);
	}
	
	@StreamListener(Sink.INPUT)
	public void handle(Person person) {
		LOG.info("Name - " + person.getName());
	}
}
