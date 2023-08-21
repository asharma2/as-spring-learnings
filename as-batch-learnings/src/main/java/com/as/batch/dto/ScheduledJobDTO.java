package com.as.batch.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.as.batch.scheduler.PingJob;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ScheduledJobDTO {

	private Long id;
	private String name;
	private String group;
	private String jobClass;
	private String cronExpression;
	private Boolean cronJob;
	private Long repeatInterval;
	private Integer repeatCount;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	private Boolean active;
	private Set<ScheduledJobParamsDTO> jobParams;

	public static final ScheduledJobDTO ofPingSimpleSchedule(LocalDate startDt, LocalDate endDt, long repeatInterval,
			int repeatCout) {
		ScheduledJobDTO job = new ScheduledJobDTO();
		job.setActive(true);
		job.setGroup("SIMPLE");
		job.setCronJob(false);
		job.setJobClass(PingJob.class.getName());
		job.setName("PING_SIMPLE_SCHEDULE");
		job.setRepeatCount(repeatCout);
		job.setRepeatInterval(repeatInterval);
		job.setEndDate(endDt);
		job.setStartDate(startDt);

		Set<ScheduledJobParamsDTO> params = new HashSet<>();
		params.add(ScheduledJobParamsDTO.of("JOB_NAME", "PING"));
		params.add(ScheduledJobParamsDTO.of("JOB_TYPE", "SIMPLE"));

		job.setJobParams(params);
		return job;

	}

	public static final ScheduledJobDTO ofPingCronSchedule(LocalDate startDt, LocalDate endDt, String cron) {
		ScheduledJobDTO job = new ScheduledJobDTO();
		job.setActive(true);
		job.setGroup("CRON");
		job.setCronJob(true);
		job.setCronExpression(cron);
		job.setJobClass(PingJob.class.getName());
		job.setName("PING_SIMPLE_SCHEDULE");
		job.setRepeatCount(0);
		job.setRepeatInterval(0L);
		job.setEndDate(endDt);
		job.setStartDate(startDt);

		Set<ScheduledJobParamsDTO> params = new HashSet<ScheduledJobParamsDTO>();
		params.add(ScheduledJobParamsDTO.of("JOB_NAME", "PING"));
		params.add(ScheduledJobParamsDTO.of("JOB_TYPE", "CRON"));

		job.setJobParams(params);
		return job;

	}

}
