package com.as.batch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.as.batch.domain.EmployeeDocument;
import com.as.batch.repository.EmployeeDocumentRepository;

@Service
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

	@Autowired
	private EmployeeDocumentRepository employeeDocumentRepository;

	@Override
	public EmployeeDocument save(EmployeeDocument employeeDocument) {
		return employeeDocumentRepository.save(employeeDocument);
	}

	@Override
	public Optional<EmployeeDocument> findById(Long id) {
		return employeeDocumentRepository.findById(id);
	}

	@Override
	public Optional<EmployeeDocument> findByEmployeeIdAndName(Long employeeId, String name) {
		return employeeDocumentRepository.findByEmployeeIdAndName(employeeId, name);
	}

	@Override
	public List<EmployeeDocument> findByEmployeeId(Long employeeId) {
		return employeeDocumentRepository.findByEmployeeId(employeeId);
	}

	@Override
	public void deleteById(Long id) {
		employeeDocumentRepository.deleteById(id);
	}

	@Override
	public void update(Long id, EmployeeDocument employeeDocument) {
		employeeDocument.setId(id);
		employeeDocumentRepository.save(employeeDocument);
	}

}
