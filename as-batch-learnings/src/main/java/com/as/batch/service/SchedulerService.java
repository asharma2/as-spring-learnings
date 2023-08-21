package com.as.batch.service;

import java.util.Optional;

import com.as.batch.domain.ScheduledJob;

public interface SchedulerService {

	void saveAndSchedule(ScheduledJob job);

	Optional<ScheduledJob> findByNameAndGroup(String name, String group);

	void deleteAndUnschedule(ScheduledJob job);

	void updateJob(ScheduledJob job);

	void triggerJob(ScheduledJob job);
}
