/**
 * 
 */
package com.tgc.springbatch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author rtalapaneni
 *
 */
@EnableBatchProcessing
@Configuration
public class SCDFSourceConfiguration {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private SCDFSourceDirectoryReader reader;
	
	@Autowired
	private SCDFSourceProcesser processer;
	
	@Autowired
	private SCDFItemStreamWriter writer;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public Job buildJob() {
		return jobBuilderFactory.get("sourceJob")
				.incrementer(new RunIdIncrementer())
				.start(buildStep())
				.build();
	}
	
	@Bean
	public Step buildStep() {
		
		return stepBuilderFactory.get("sourceStep")
				.<SimpleBean, SimpleBean>chunk(1)
				.reader(reader)
				.processor(processer)
				.writer(writer)
				.build();
	}

}
