package com.as.batch.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator implements Validator {

	private static final Integer MB = 1024 * 1024;

	private boolean isSupportedContentType(String contentType) {
		return contentType.equals("text/xml") || contentType.equals("application/pdf")
				|| contentType.equals("image/png") || contentType.equals("image/jpg")
				|| contentType.equals("image/jpeg");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MultipartFile.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MultipartFile multipart = (MultipartFile) target;
		String contentType = multipart.getContentType();
		if (!isSupportedContentType(contentType)) {
			errors.reject("F001", "Content-Type not supported");
		} else if (multipart.getSize() > 1 * MB) {
			errors.reject("F002", "Size greater than given size");
		}
	}
}
