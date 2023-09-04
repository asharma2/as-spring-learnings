package com.as.batch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDocumentDTO {

	private Long employeeId;
	@NotBlank(message = "Name can't be blank")
	private String name;
	@NotBlank(message = "Type can't be blank")
	private String type;
}
