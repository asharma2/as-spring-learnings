package com.as.batch.dto;

import lombok.Data;

@Data
public class ErrorDTO {

	int code;
	String field;
	String error;
}
