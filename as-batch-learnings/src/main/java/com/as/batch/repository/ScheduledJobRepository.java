package com.as.batch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.batch.domain.ScheduledJob;

public interface ScheduledJobRepository extends JpaRepository<ScheduledJob, Long> {

	Optional<ScheduledJob> findByNameAndGroup(String name, String group);
}
