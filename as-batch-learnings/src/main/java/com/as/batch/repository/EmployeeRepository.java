package com.as.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.batch.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
