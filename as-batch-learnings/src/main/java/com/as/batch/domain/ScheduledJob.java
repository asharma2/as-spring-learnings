package com.as.batch.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.as.batch.scheduler.PingJob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SCH_JOB")
public class ScheduledJob {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "JOB_NAME")
	private String name;
	@Column(name = "JOB_GROUP")
	private String group;
	@Column(name = "JOB_CLASS")
	private String jobClass;
	@Column(name = "CRON_EXPRESSION")
	private String cronExpression;
	@Column(name = "IS_CRON")
	private Boolean cronJob;
	@Column(name = "REPEAT_INTERVAL")
	private Long repeatInterval;
	@Column(name = "REPEAT_COUNT")
	private Integer repeatCount;
	@Column(name = "START_DATE")
	private LocalDate startDate;
	@Column(name = "END_DATE")
	private LocalDate endDate;
	@Column(name = "CREATED_TS")
	private LocalDateTime createdTs;
	@Column(name = "MODIFIED_TS")
	private LocalDateTime modifiedTs;
	@Column(name = "ACTIVE")
	private Boolean active;
	@OneToMany(mappedBy = "scheduledJob", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<ScheduledJobParams> jobParams;

	public static final ScheduledJob ofPingSimpleSchedule(LocalDate startDt, LocalDate endDt, long repeatInterval,
			int repeatCout) {
		ScheduledJob job = new ScheduledJob();
		job.setActive(true);
		job.setCreatedTs(LocalDateTime.now());
		job.setGroup("SIMPLE");
		job.setCronJob(false);
		job.setJobClass(PingJob.class.getName());
		job.setModifiedTs(LocalDateTime.now());
		job.setName("PING_SIMPLE_SCHEDULE");
		job.setRepeatCount(repeatCout);
		job.setRepeatInterval(repeatInterval);
		job.setEndDate(endDt);
		job.setStartDate(startDt);

		Set<ScheduledJobParams> params = new HashSet<ScheduledJobParams>();
		params.add(ScheduledJobParams.of("JOB_NAME", "PING"));
		params.add(ScheduledJobParams.of("JOB_TYPE", "SIMPLE"));

		job.setJobParams(params);
		return job;

	}

	public static final ScheduledJob ofPingCronSchedule(LocalDate startDt, LocalDate endDt, String cron) {
		ScheduledJob job = new ScheduledJob();
		job.setActive(true);
		job.setCreatedTs(LocalDateTime.now());
		job.setGroup("CRON");
		job.setCronJob(true);
		job.setCronExpression(cron);
		job.setJobClass(PingJob.class.getName());
		job.setModifiedTs(LocalDateTime.now());
		job.setName("PING_CRON_SCHEDULE");
		job.setRepeatCount(0);
		job.setRepeatInterval(0L);
		job.setEndDate(endDt);
		job.setStartDate(startDt);

		Set<ScheduledJobParams> params = new HashSet<ScheduledJobParams>();
		params.add(ScheduledJobParams.of("JOB_NAME", "PING"));
		params.add(ScheduledJobParams.of("JOB_TYPE", "CRON"));

		job.setJobParams(params);
		return job;

	}

}
