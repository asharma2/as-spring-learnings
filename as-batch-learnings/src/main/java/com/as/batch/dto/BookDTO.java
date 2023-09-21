package com.as.batch.dto;

import java.util.Collections;
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

	public static BookDTO ofBook(String author, Integer publishedYear, Integer pages, String name,
			List<String> categories) {
		BookDTO dto = new BookDTO();
		dto.setAuthor(author);
		dto.setPublishedYear(publishedYear);
		dto.setPages(pages);
		dto.setName(name);
		dto.setCategories(categories);
		return dto;
	}

	public static BookDTO ofBook(String author, Integer publishedYear, Integer pages, String name) {
		return ofBook(author, publishedYear, pages, name, Collections.emptyList());
	}

}
