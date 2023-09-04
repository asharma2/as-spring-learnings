package com.as.batch;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
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
