package com.as.batch.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.as.batch.dto.EmployeeDocumentDTO;
import com.as.batch.validator.FileSize;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/document")
public class EmployeeDocumentController {

	@PostMapping(value = "/employee-v1", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> create(@Valid @RequestPart("document") EmployeeDocumentDTO request,
			@RequestPart("file") @FileSize(maxSizeInMB = 1) MultipartFile file) {
		log.info("Payload: {}, File: {}", request, file);
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/employee-v2", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> createV2(
			@RequestPart("employeeId") @NotBlank(message = "EmployeeId can't be left blank") Long employeeId,
			@Valid @RequestPart("documentName") @NotBlank(message = "Document name can't be left blank") String name,
			@RequestPart("documentType") @NotBlank(message = "Document type can't be left blank") String type,
			@RequestPart("file") @FileSize(maxSizeInMB = 1) MultipartFile file) {
		log.info("Payload: {}, File: {}", employeeId + "," + name + "," + type, file);
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/employee-v3", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> createV3(
			@Valid @RequestParam("employeeId") @NotBlank(message = "EmployeeId can't be left blank") Long employeeId,
			@Valid @RequestParam("documentName") @NotBlank(message = "Document name can't be left blank") String name,
			@Valid @RequestParam("documentType") @NotBlank(message = "Document type can't be left blank") String type,
			@RequestParam("file") @FileSize(maxSizeInMB = 1) MultipartFile file) {
		log.info("Payload: {}, File: {}", employeeId + "," + name + "," + type, file);
		return ResponseEntity.ok().build();
	}
}
