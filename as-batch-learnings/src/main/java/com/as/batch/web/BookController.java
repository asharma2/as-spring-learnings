package com.as.batch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.as.batch.dto.BookDTO;
import com.as.batch.exception.FieldValidationException;
import com.as.batch.validator.FileValidator;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * https://segmentfault.com/a/1190000040378044/en
 * 
 * DispatcherServlet -> HandlerMapping {url, http protocol, request parameters,
 * etc.} -> HandlerAdapter -> ViewResolver
 * 
 * 
 * 
 * RequestMappingHandlerAdapter ->
 * ServletInvocableHandlerMethod.invokeHandlerMethod() extends
 * InvocableHandlerMethod
 * 
 * 
 * HandlerMethod -> HandlerInterceptor ->
 * 
 * HandlerMethod itself only encapsulates and stores data and does not provide
 * specific usage methods, so InvocableHandlerMethod appears, which is
 * responsible for executing HandlerMethod , and ServletInvocableHandlerMethod
 * adds processing of return values and response status codes on its basis.
 * 
 * 
 * pringMVC itself provides a lot of HandlerMethodArgumentResolver
 * implementation classes, such as:
 * 
 * RequestResponseBodyMethodProcessor ( @RequestBody annotated by
 * 060f7b86991b38)
 * 
 * RequestParamMethodArgumentResolver ( @RequestParam annotated by
 * 060f7b86991b4d, or Java basic data types that are not matched by other
 * Resolver)
 * 
 * RequestHeaderMethodArgumentResolver ( @RequestHeaderMethodArgumentResolver
 * annotated by 060f7b86991b63)
 * 
 * ServletModelAttributeMethodProcessor ( @ModelAttribute annotated by
 * 060f7b86991b84, or custom objects that are not matched by other resolvers)
 * and so on.
 * 
 * Let's take ServletModelAttributeMethodProcessor as an example to see how its
 * resolveArgument like:
 * 
 * 
 * https://www.wimdeblauwe.com/blog/2022/02/23/spring-boot-request-parameters-validation/
 * 
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

	@Autowired
	private FileValidator fileValidator;

	@PostMapping(value = "/requestpart", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createUsingRequestPart(@Valid @RequestPart("book") BookDTO bookDTO,
			@Valid @RequestPart("file") MultipartFile file, BindingResult bindingResult) {
		log.info("Input => Book: {}, File: {}, ContentType: {}", bookDTO, file.getName(), file.getContentType());
		fileValidator.validate(file, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new FieldValidationException(bindingResult);
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping(value = "/requestbody", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createUsingRequestBody(@Valid @RequestBody BookDTO bookDTO) {
		log.info("Input => Book: {}", bookDTO);
		return ResponseEntity.ok().build();
	}
}
