package com.as.batch.factory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class JobFactory {

	@Autowired
	private ApplicationContext applicationContext;

	public JobDetail create(Class<? extends Job> jobClass, boolean durable, String name, String group,
			Map<String, Object> params) {

		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(durable);
		factoryBean.setApplicationContext(applicationContext);
		factoryBean.setName(name);
		factoryBean.setGroup(group);

		JobDataMap metadata = new JobDataMap();
		params.forEach((k, v) -> {
			metadata.put(k, v);
		});

		factoryBean.setJobDataMap(metadata);
		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();

	}

	public SimpleTrigger createSimpleTrigger(String triggerName, LocalDate startDate, LocalDate endDate,
			Long repeatInterval, Integer repeatCount, int misFireInstruction) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setName(triggerName);
		factoryBean.setRepeatInterval(repeatInterval);
		factoryBean.setRepeatCount(repeatCount);
		factoryBean.setMisfireInstruction(misFireInstruction);
		factoryBean.afterPropertiesSet();
		SimpleTriggerImpl simpleTrigger = (SimpleTriggerImpl) factoryBean.getObject();
		simpleTrigger.setStartTime(new Date());
		if (startDate != null) {
			simpleTrigger.setStartTime(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}
		if (endDate != null) {
			simpleTrigger.setEndTime(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}
		return simpleTrigger;
	}

	public CronTrigger createCronTrigger(String triggerName, LocalDate startDate, LocalDate endDate,
			String cronExpression, int misFireInstruction) throws Exception {
		CronTriggerFactoryBean cronTriggerFactory = new CronTriggerFactoryBean();
		cronTriggerFactory.setName(triggerName);
		cronTriggerFactory.setCronExpression(cronExpression);
		cronTriggerFactory.setMisfireInstruction(misFireInstruction);

		cronTriggerFactory.afterPropertiesSet();
		final CronTriggerImpl cronTrigger = (CronTriggerImpl) cronTriggerFactory.getObject();
		cronTrigger.setStartTime(new Date());

		if (startDate != null) {
			cronTrigger.setStartTime(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}
		if (endDate != null) {
			cronTrigger.setEndTime(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}
		Date fireAgainTime = cronTrigger.getFireTimeAfter(cronTrigger.getStartTime());
		if (fireAgainTime == null)
			throw new IllegalArgumentException("Cron can't be fired again. Expression: " + cronExpression + " EndTime: "
					+ cronTrigger.getEndTime());
		return cronTrigger;
	}

}
