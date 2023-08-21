package com.as.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.batch.domain.ScheduledJobParams;

public interface ScheduledJobParamsRepository extends JpaRepository<ScheduledJobParams, Long> {

}
