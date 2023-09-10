package com.as.batch.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BookDTO {

	@NotEmpty(message = "Author can't be blank")
	String author;
	@Min(value = 1990, message = "Publish year should be greater that 1989")
	Integer publishedYear;
	Integer pages;
	@NotEmpty(message = "Name can't be left blank")
	String name;
	List<String> categories;

}
