package com.tgc.springbatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScdfSinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScdfSinkApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner sink(ICustomerRepository repository) {
		return args -> {
			
		};
	}
}
