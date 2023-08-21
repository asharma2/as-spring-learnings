package com.as.batch.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.as.batch.domain.Employee;
import com.as.batch.domain.ScheduledJob;
import com.as.batch.domain.ScheduledJobParams;
import com.as.batch.dto.EmployeeDTO;
import com.as.batch.dto.ScheduledJobDTO;
import com.as.batch.dto.ScheduledJobParamsDTO;

@Mapper(componentModel = "spring", imports = { LocalDateTime.class })
public interface DomainMapper {

	@Mapping(target = "createdTs", expression = "java(LocalDateTime.now())")
	@Mapping(target = "modifiedTs", expression = "java(LocalDateTime.now())")
	ScheduledJob mapScheduledJob(ScheduledJobDTO scheduledJob);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "scheduledJob", ignore = true)
	ScheduledJobParams mapScheduledJobParams(ScheduledJobParamsDTO scheduledJobParams);

	Employee mapEmployee(EmployeeDTO dto);

}
