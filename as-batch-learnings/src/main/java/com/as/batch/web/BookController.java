package com.as.batch.web;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.as.batch.dto.BookDTO;
import com.as.batch.dto.ResponseDTO;
import com.as.batch.exception.ApplicationException;
import com.as.batch.exception.FieldValidationException;
import com.as.batch.validator.FileValidator;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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
 * https://blog.devgenius.io/resilience4j-circuit-breaker-with-spring-boot-5fb19a748323
 * 
 * https://bootcamptoprod.com/resilience4j-rate-limiter/
 * 
 * https://bootcamptoprod.com/spring-boot-resilience4j-retry/
 * 
 * https://jsession4d.com/a-quick-guide-to-resilience4j-with-spring-boot/
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

	@GetMapping("/list")
	@RateLimiter(name = "bookGetResponseRT", fallbackMethod = "rateLimitingFallback")
	@CircuitBreaker(name = "bookGetResponseCB", fallbackMethod = "bookResponseFallback")
	public ResponseEntity<?> getResponse(@RequestParam("code") String code) {
		log.info("========== GET RESPONSE =============");
		if ("S".equals(code)) {
			return ResponseEntity.ok(ResponseDTO.ofSuccess(200, "Book List",
					Arrays.asList(BookDTO.ofBook("Paul Caelo", 2023, 1, "Monk Who Sold His Ferrari"),
							BookDTO.ofBook("Paul Caelo", 2023, 2, "Monk Who Sold His Ferrari"))));
		}
		throw new ApplicationException();
	}

	public ResponseEntity<?> bookResponseFallback(String code, Throwable cause) {
		log.info("========== GET FALLBACK RESPONSE =============");
		return ResponseEntity.ok(ResponseDTO.ofSuccess(101, "Book Generic Exception", Collections.emptyList()));
	}

	public ResponseEntity<?> bookResponseRateLimitFallback(String code, Throwable cause) {
		log.info("========== GET FALLBACK RATELIMIT RESPONSE =============");
		return ResponseEntity.ok(ResponseDTO.ofSuccess(100, "Book Rate Limit", Collections.emptyList()));
	}

	public ResponseEntity<?> rateLimitingFallback(String code, RequestNotPermitted ex) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Retry-After", "60s"); // retry after one second

		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).headers(responseHeaders) // send retry header
				.body("Too Many Requests - Retry After 1 Minute");
	}
}
