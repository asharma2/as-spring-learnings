package com.as.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(staticName = "of")
public class ContactEntity extends BaseEntity {

	private Long id;
	private String countryCode;
	private String number;
	private ContactType contactType;
}
