package com.tgc.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@SpringBootApplication
public class ScdfSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScdfSourceApplication.class, args);
	}
}
