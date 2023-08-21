package com.as.batch.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.batch.dto.EmployeeDTO;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

	@GetMapping
	public ResponseEntity<?> get() {
		return ResponseEntity.ok(EmployeeDTO.ofLoad(1000));
	}

}
