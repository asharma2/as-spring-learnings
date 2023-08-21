package com.as.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.as.batch.domain.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee item) throws Exception {
		log.info("Item => {}", item);
		return item;
	}

}
