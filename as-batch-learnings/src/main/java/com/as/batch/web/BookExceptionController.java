package com.as.batch.web;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.as.batch.dto.ErrorDTO;
import com.as.batch.dto.ResponseDTO;
import com.as.batch.exception.BookAlreadyExist;
import com.as.batch.exception.FieldValidationException;
import com.as.batch.mapper.DTOMapper;

@ControllerAdvice
public class BookExceptionController {

	@Autowired
	private DTOMapper dtoMapper;

	@ExceptionHandler(value = BookAlreadyExist.class)
	public ResponseEntity<Object> exception(BookAlreadyExist exception) {
		return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> exception(MethodArgumentNotValidException exception) {
		if (exception.getAllErrors() != null) {
			exception.getAllErrors().forEach(e -> {
				System.err.println(e);
			});
		}
		if (exception.getBindingResult().getFieldErrors() != null) {
			exception.getBindingResult().getFieldErrors().forEach(fe -> {
				System.err.println("Field: " + fe.getField() + ", Message: " + fe.getDefaultMessage() + ", Rejected: "
						+ fe.getRejectedValue());
			});
		}

		List<ErrorDTO> errorDTOs = exception.getBindingResult().getFieldErrors().stream()
				.map(x -> dtoMapper.mapError(x)).collect(Collectors.toList());

		return new ResponseEntity<>(ResponseDTO.ofFailed(HttpStatus.BAD_REQUEST.value(), errorDTOs),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = FieldValidationException.class)
	public ResponseEntity<Object> exception(FieldValidationException exception) {

		if (!CollectionUtils.isEmpty(exception.getConstrainVoilations())) {
			List<ErrorDTO> errorDTOs = exception.getConstrainVoilations().stream().map(x -> dtoMapper.mapError(x))
					.collect(Collectors.toList());

			return new ResponseEntity<>(ResponseDTO.ofFailed(HttpStatus.BAD_REQUEST.value(), errorDTOs),
					HttpStatus.BAD_REQUEST);
		}
		if (Objects.nonNull(exception.getBindingResult())) {
			List<ErrorDTO> errorDTOs = exception.getBindingResult().getAllErrors().stream()
					.map(x -> dtoMapper.mapError(x)).collect(Collectors.toList());
			return new ResponseEntity<>(ResponseDTO.ofFailed(HttpStatus.BAD_REQUEST.value(), errorDTOs),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ResponseDTO.ofFailed(HttpStatus.BAD_REQUEST.value(), Collections.emptyList()),
				HttpStatus.BAD_REQUEST);
	}

}
