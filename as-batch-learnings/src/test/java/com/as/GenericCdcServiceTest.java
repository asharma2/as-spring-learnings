package com.as;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.as.batch.domain.AddressEntity;
import com.as.batch.domain.AddressType;
import com.as.batch.domain.ContactEntity;
import com.as.batch.domain.ContactType;
import com.as.batch.domain.StudentEntity;
import com.as.batch.domain.UniversityEntity;
import com.as.batch.service.CdcService;
import com.as.batch.service.GenericCdcService;

public class GenericCdcServiceTest {

	public static void main(String[] args) {
		List<CdcService.CdcRecord> records = new ArrayList<CdcService.CdcRecord>();
		GenericCdcService cdcService = new GenericCdcService();
		AddressEntity o = AddressEntity.of(1L, "S1", "S2", "Noida", "UP", "IND", AddressType.PERMANENT);
		o.setActive(true);
		AddressEntity n = AddressEntity.of(2L, "S1", "S3", "Noida", "UP", "USA", AddressType.OFFICIAL);
		n.setActive(true);

		List<ContactEntity> contacts = Arrays.asList(ContactEntity.of(1L, "+91", "8588943230", ContactType.OFFICE),
				ContactEntity.of(2L, "+91", "8588943231", ContactType.OFFICE));

		UniversityEntity universityEntity = UniversityEntity.of(1L, "SKIT", o, contacts);

		StudentEntity se1 = StudentEntity.of(1L, "A", "asharma", universityEntity, Arrays.asList(o, n), contacts);
		StudentEntity se2 = StudentEntity.of(1L, "B", "asharma", universityEntity, Arrays.asList(o, n), contacts);

		cdcService.detectChanges(se1, se2, records);
		System.out.println("Records: " + records);
	}
}
