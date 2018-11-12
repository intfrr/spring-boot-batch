/**
 * 
 */
package com.tgc.springbatch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

/**
 * @author rtalapaneni
 *
 */
@Configuration
@EnableBatchProcessing
public class SinkBatchConfiguration {
	private static final Logger LOG = LoggerFactory.getLogger(SinkBatchConfiguration.class);
	private static final Integer CHUNK_SIZE = 100;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private ICustomerRepository customerRepository; 

	public Job sinkJob() {
		LOG.info("Starting sink job");
		return this.jobBuilderFactory.get("sinkJob")
				.incrementer(new RunIdIncrementer())
				.start(sinkData())
				.build();
	}
	
	public Step sinkData() {
		return stepBuilderFactory.get("sinkStep")
				.<Customer, Customer>chunk(CHUNK_SIZE)
				.reader(customerReader())
				.writer(customerWriter())
				.build();
	}
	
	@Bean
	public FlatFileItemReader<Customer> customerReader() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id", "name"});
		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
		FieldSetMapper<Customer> fieldSetMapper = new FieldSetMapper<Customer>() {

			@Override
			public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
				if(fieldSet == null){
					return null;
				}
				
				Customer customer = new Customer();
				customer.setId(fieldSet.readLong("id"));
				customer.setName(fieldSet.readString("name"));
				return customer;
			}
		};
		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);

		return new FlatFileItemReaderBuilder<Customer>().name("customerFileItemReader")
				.resource(new ClassPathResource("input/customer.csv")).lineMapper(lineMapper).lineTokenizer(lineTokenizer)
				.build();
	}
	
	@Bean
	public ItemWriter<Customer> customerWriter() {
		ItemWriter<Customer> itemWriter = new ItemWriter<Customer>() {
			@Override
			public void write(List<? extends Customer> items) throws Exception {
				customerRepository.saveAll(items);
			}
		};
		return itemWriter;
	}
}
