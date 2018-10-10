package edu.balu.self;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@SpringBootApplication
public class MultiStepBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiStepBatchApplication.class, args);
	}
}
