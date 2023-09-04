package com.as.batch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.batch.domain.EmployeeDocument;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {

	Optional<EmployeeDocument> findByEmployeeIdAndName(Long employeeId, String name);

	List<EmployeeDocument> findByEmployeeId(Long employeeId);

}
