package com.as.batch.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmployeeDTO {

	private Long id;
	private String name;
	private String organization;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate joiningDate;

	public static final List<EmployeeDTO> ofLoad(long load) {
		List<EmployeeDTO> dtos = new ArrayList<EmployeeDTO>();
		for (long i = 1; i <= load; i++) {
			dtos.add(EmployeeDTO.of(i, "A" + i, "IT", LocalDate.now().minusDays(load).plusDays(i)));
		}
		return dtos;
	}
}
