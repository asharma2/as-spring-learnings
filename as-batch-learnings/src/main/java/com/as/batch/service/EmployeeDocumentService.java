package com.as.batch.service;

import java.util.List;
import java.util.Optional;

import com.as.batch.domain.EmployeeDocument;

public interface EmployeeDocumentService {

	EmployeeDocument save(EmployeeDocument employeeDocument);

	Optional<EmployeeDocument> findById(Long id);

	Optional<EmployeeDocument> findByEmployeeIdAndName(Long employeeId, String name);

	List<EmployeeDocument> findByEmployeeId(Long employeeId);

	void deleteById(Long id);

	void update(Long id, EmployeeDocument employeeDocument);

}
