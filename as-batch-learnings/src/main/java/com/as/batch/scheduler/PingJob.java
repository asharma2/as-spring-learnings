package com.as.batch.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PingJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String key = context.getJobDetail().getKey().getName();
		String group = context.getJobDetail().getKey().getGroup();

		log.info("PingJob => Key: {}, Group: {}", key, group);
	}

}
