package com.as.batch;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.as.batch.domain.ScheduledJob;
import com.as.batch.service.SchedulerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class LearningsBatchApp implements ApplicationRunner {

	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	@Qualifier("employeeJob")
	private Job job;

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

			jobLauncher.run(job, newExecution());

		} catch (Exception e) {
			log.error("Job Failed => Cause: ", e);
		}
	}

	private JobParameters newExecution() {
		return new JobParametersBuilder().addDate("currentTime", Date.from(Instant.now())).toJobParameters();
	}
}
