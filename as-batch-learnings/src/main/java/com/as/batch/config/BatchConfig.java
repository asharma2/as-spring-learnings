package com.as.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import com.as.batch.domain.Employee;
import com.as.batch.mapper.DomainMapper;
import com.as.batch.processor.EmployeeProcessor;
import com.as.batch.reader.EmployeeReader;
import com.as.batch.writer.EmployeeWriter;

/**
 * SQL ->
 * https://github.com/spring-projects/spring-batch/tree/main/spring-batch-core/src/main/resources/org/springframework/batch/core
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Bean
	@Primary
	public JobRepository jobRepository(DataSource datasource, PlatformTransactionManager transactionManager)
			throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(datasource);
		factory.setTablePrefix("BATCH_");
		factory.setTransactionManager(transactionManager);
		factory.setIncrementerFactory(null);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public ItemReader<Employee> itemReader(Environment env, RestTemplate restTemplate, DomainMapper domainMapper) {
		return new EmployeeReader("http://localhost:8085/api/v1/employee", restTemplate, domainMapper);
	}

	@Bean
	public ItemWriter<Employee> itemWriter() {
		return new EmployeeWriter();
	}

	@Bean
	public ItemProcessor<Employee, Employee> itemProcessor() {
		return new EmployeeProcessor();
	}

	@Bean
	public Step employeeJobStep(ItemReader<Employee> reader, ItemProcessor<Employee, Employee> processor,
			ItemWriter<Employee> writer, PlatformTransactionManager transactionManager, JobRepository jobRepository) {
		return new StepBuilder("employee-job-step", jobRepository).<Employee, Employee>chunk(10, transactionManager)
				.reader(reader).processor(processor).writer(writer).build();
	}

	@Bean
	@Qualifier("employeeJob")
	public Job runJob(Step employeeJobStep, JobRepository jobRepository) {
		return new JobBuilder("employee-Job", jobRepository).start(employeeJobStep).build();
	}

}
