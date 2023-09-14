package com.as.batch.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(staticName = "of")
public class UniversityEntity extends BaseEntity {

	private Long id;
	private String name;
	private AddressEntity address;
	private List<ContactEntity> contacts;
}
