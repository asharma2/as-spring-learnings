package com.as.batch.mapper;

import org.mapstruct.Mapper;

import com.as.batch.domain.Employee;
import com.as.batch.domain.ScheduledJob;
import com.as.batch.domain.ScheduledJobParams;
import com.as.batch.dto.EmployeeDTO;
import com.as.batch.dto.ScheduledJobDTO;
import com.as.batch.dto.ScheduledJobParamsDTO;

@Mapper(componentModel = "spring")
public interface DTOMapper {

	ScheduledJobDTO mapScheduledJob(ScheduledJob scheduledJob);

	ScheduledJobParamsDTO mapScheduledJobParams(ScheduledJobParams scheduledJobParams);

	EmployeeDTO mapEmployee(Employee employee);
}
