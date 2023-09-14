package com.as.batch.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(staticName = "of")
public class TeacherEntity extends BaseEntity {

	private Long id;
	private String name;
	private String username;
	private String specializiation;
	private UniversityEntity university;
	private List<AddressEntity> addresses;
	private List<ContactEntity> contacts;
}
