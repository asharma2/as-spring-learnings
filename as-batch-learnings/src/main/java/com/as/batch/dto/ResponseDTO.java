package com.as.batch.dto;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
@JsonInclude(value = Include.NON_NULL)
public class ResponseDTO<T> {

	int code;
	String msg;
	T response;
	List<ErrorDTO> errors;

	public static <T> ResponseDTO<T> ofSuccess(int code, String msg, T response) {
		return new ResponseDTO<T>(code, msg, response, Collections.emptyList());
	}

	public static <T> ResponseDTO<T> ofFailed(int code, List<ErrorDTO> errors) {
		return new ResponseDTO<T>(code, null, null, errors);
	}
}
