package com.as.batch.exception;

import java.util.Set;

import org.springframework.validation.BindingResult;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

@Getter
public class FieldValidationException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	private Set<ConstraintViolation<?>> constrainVoilations;
	private BindingResult bindingResult;

	public FieldValidationException(Set<ConstraintViolation<?>> constrainVoilations) {
		this.constrainVoilations = constrainVoilations;
	}

	public FieldValidationException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

}
