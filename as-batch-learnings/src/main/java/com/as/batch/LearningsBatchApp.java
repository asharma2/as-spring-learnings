package com.as.batch;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * https://stackoverflow.com/questions/29677589/proper-mapping-between-java-localdatetime-and-db
 * 
 * https://medium.com/@ravikumar7518989121/spring-boot-openai-chatgpt-api-integration-16e0b4608ad9
 * 
 * https://medium.com/@virendra-oswal/rate-limit-apis-with-bucket4j-via-java-a031450e1298
 * 
 * https://medium.com/@mertcakmak2/redis-cluster-integration-in-spring-boot-af9e1fab574e
 */

@Slf4j
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class LearningsBatchApp implements ApplicationRunner {

//	@Autowired
//	private SchedulerService schedulerService;
//	@Autowired
//	private JobLauncher jobLauncher;
//	@Autowired
//	@Qualifier("employeeJob")
//	private Job job;

	public static void main(String[] args) {
		SpringApplication.run(LearningsBatchApp.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
//			schedulerService.saveAndSchedule(
//					ScheduledJob.ofPingCronSchedule(LocalDate.now(), LocalDate.now().plusDays(1), "0 0/5 * * * ?"));
//
//			schedulerService.saveAndSchedule(
//					ScheduledJob.ofPingSimpleSchedule(LocalDate.now(), LocalDate.now().plusDays(1), 3000, 5));

//			jobLauncher.run(job, newExecution());

		} catch (Exception e) {
			log.error("Job Failed => Cause: ", e);
		}
	}

//	private JobParameters newExecution() {
//		return new JobParametersBuilder().addDate("currentTime", Date.from(Instant.now())).toJobParameters();
//	}
}
