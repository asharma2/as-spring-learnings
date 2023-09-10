package com.as.batch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.as.batch.domain.Employee;
import com.as.batch.domain.ScheduledJob;
import com.as.batch.domain.ScheduledJobParams;
import com.as.batch.dto.EmployeeDTO;
import com.as.batch.dto.ErrorDTO;
import com.as.batch.dto.ScheduledJobDTO;
import com.as.batch.dto.ScheduledJobParamsDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;

@Mapper(componentModel = "spring", imports = { Path.class })
public interface DTOMapper {

	ScheduledJobDTO mapScheduledJob(ScheduledJob scheduledJob);

	ScheduledJobParamsDTO mapScheduledJobParams(ScheduledJobParams scheduledJobParams);

	EmployeeDTO mapEmployee(Employee employee);

	@Mapping(target = "code", constant = "400")
	@Mapping(target = "field", source = "field")
	@Mapping(target = "error", source = "defaultMessage")
	ErrorDTO mapError(FieldError fieldError);

	@Mapping(target = "code", constant = "400")
	@Mapping(target = "error", source = "messageTemplate")
	@Mapping(target = "field", expression = "java(constraintViolation.getPropertyPath().toString())")
	ErrorDTO mapError(ConstraintViolation<?> constraintViolation);

	@Mapping(target = "code", constant = "400")
	@Mapping(target = "field", constant = "file")
	@Mapping(target = "error", source = "defaultMessage")
	ErrorDTO mapError(ObjectError error);

}
