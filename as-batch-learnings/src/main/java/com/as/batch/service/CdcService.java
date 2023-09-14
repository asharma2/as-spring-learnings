package com.as.batch.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

public interface CdcService {

	@Data
	class CdcRecord {
		Class<?> rootClass;
		Map<String, Delta> deltas;
	}

	@Data
	@AllArgsConstructor(staticName = "of")
	class Delta {
		Object oldValue;
		Object newValue;
		Type type;
	}

	void detectChanges(Object oldEntity, Object newEntity, List<CdcRecord> cdcRecords);

}
