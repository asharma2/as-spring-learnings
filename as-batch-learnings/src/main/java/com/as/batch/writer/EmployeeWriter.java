package com.as.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.as.batch.domain.Employee;
import com.as.batch.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeWriter implements ItemWriter<Employee> {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void write(Chunk<? extends Employee> chunk) throws Exception {
		log.info("Chunk => {}", chunk);
		employeeRepository.saveAll(chunk.getItems());
	}

}
