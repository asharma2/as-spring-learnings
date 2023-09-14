package com.as.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(staticName = "of")
public class AddressEntity extends BaseEntity {

	private Long id;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String country;
	private AddressType addressType;
}
