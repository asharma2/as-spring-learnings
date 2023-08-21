package com.as.batch.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.as.batch.domain.ScheduledJob;
import com.as.batch.domain.ScheduledJobParams;
import com.as.batch.exception.SchedulerException;
import com.as.batch.factory.JobFactory;
import com.as.batch.repository.ScheduledJobParamsRepository;
import com.as.batch.repository.ScheduledJobRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	private ScheduledJobRepository scheduledJobRepository;

	@Autowired
	private ScheduledJobParamsRepository scheduledJobParamsRepository;

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private JobFactory jobFactory;

	@SuppressWarnings("unchecked")
	@Override
	public void saveAndSchedule(ScheduledJob job) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			Class<?> jobClass = Class.forName(job.getJobClass());
			JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) jobClass)
					.withIdentity(job.getName(), job.getGroup()).build();
			Optional<ScheduledJob> optScheduledJob = scheduledJobRepository.findByNameAndGroup(job.getName(),
					job.getGroup());

			if (!optScheduledJob.isPresent()) {

				scheduledJobRepository.save(job);
				job.getJobParams().forEach(x -> x.setScheduledJob(job));
				scheduledJobParamsRepository.saveAll(job.getJobParams());

				if (job.getActive()) {
					if (!scheduler.checkExists(jobDetail.getKey())) {
						Trigger trigger = createTrigger(job);
						scheduler.scheduleJob(jobDetail, trigger);
						log.info("NextFireTime: {}, PreviousFireTime: {}, FinalFireTime: {}", trigger.getNextFireTime(),
								trigger.getPreviousFireTime(), trigger.getFinalFireTime());
					} else {
						throw new SchedulerException("Job already scheduled." + jobDetail.getKey());
					}
				}
			} else {
				throw new SchedulerException("Job already exists.");
			}
		} catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	private Trigger createTrigger(ScheduledJob job) throws Exception {
		if (job.getCronJob()) {
			return jobFactory.createCronTrigger(job.getName(), job.getStartDate(), job.getEndDate(),
					job.getCronExpression(), SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
		}
		return jobFactory.createSimpleTrigger(job.getName(), job.getStartDate(), job.getEndDate(),
				job.getRepeatInterval(), job.getRepeatCount(), SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
	}

	@Override
	public void deleteAndUnschedule(ScheduledJob job) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey key = new JobKey(job.getName(), job.getGroup());
			Optional<ScheduledJob> optScheduledJob = scheduledJobRepository.findByNameAndGroup(job.getName(),
					job.getGroup());

			if (optScheduledJob.isPresent()) {
				scheduledJobRepository.delete(job);
			} else {
				throw new SchedulerException("Job does not exists.");
			}

			if (scheduler.checkExists(key)) {
				scheduler.deleteJob(key);
			}
		} catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	@Override
	public void updateJob(ScheduledJob job) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			scheduler.unscheduleJob(new TriggerKey(job.getName()));
			Optional<ScheduledJob> optScheduledJob = scheduledJobRepository.findByNameAndGroup(job.getName(),
					job.getGroup());
			if (optScheduledJob.isPresent()) {

				Iterator<ScheduledJobParams> itr = optScheduledJob.get().getJobParams().iterator();
				Set<ScheduledJobParams> toDelete = new HashSet<ScheduledJobParams>();
				while (itr.hasNext()) {
					ScheduledJobParams param = itr.next();
					if (!job.getJobParams().contains(param)) {
						toDelete.add(param);
						itr.remove();
					}
				}
				job.setId(optScheduledJob.get().getId());
				scheduledJobRepository.save(job);
				job.getJobParams().forEach(x -> x.setScheduledJob(job));
				scheduledJobParamsRepository.saveAll(job.getJobParams());
			} else {
				throw new SchedulerException("Job does not exists");
			}
		} catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	@Override
	public void triggerJob(ScheduledJob job) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey key = new JobKey(job.getName(), job.getGroup());
			if (scheduler.checkExists(key)) {
				scheduler.triggerJob(key);
			}
		} catch (Exception e) {
			throw new SchedulerException(e);
		}
	}

	@Override
	public Optional<ScheduledJob> findByNameAndGroup(String name, String group) {
		return scheduledJobRepository.findByNameAndGroup(name, group);
	}

}
