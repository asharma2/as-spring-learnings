package com.as.batch.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.as.batch.dto.EmployeeDocumentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Component
public class StringToEmployeeDocumentConverter implements Converter<String, EmployeeDocumentDTO> {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	@SneakyThrows
	public EmployeeDocumentDTO convert(String source) {
		return objectMapper.readValue(source, EmployeeDocumentDTO.class);
	}

}
