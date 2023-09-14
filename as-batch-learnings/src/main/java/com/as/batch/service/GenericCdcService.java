package com.as.batch.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.as.batch.exception.CdcException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericCdcService implements CdcService {

	private Map<Class<?>, List<Field>> classAndFieldMapping = new HashMap<>();

	@Override
	public void detectChanges(Object oldEntity, Object newEntity, List<CdcRecord> cdcRecords) {
		try {

			if (oldEntity == null && newEntity == null)
				return;

			Class<?> klass = oldEntity != null ? oldEntity.getClass() : newEntity.getClass();
			log.info("Name: {}", klass.getName());
			if (!classAndFieldMapping.containsKey(klass)) {
				List<Field> fields = new ArrayList<>();
				readFieldsRecursively(klass, fields);
				classAndFieldMapping.put(klass, fields);
			}
			List<Field> fields = classAndFieldMapping.get(klass);
			CdcRecord cdcRecord = new CdcRecord();
			cdcRecord.setRootClass(klass);
			Map<String, Delta> deltas = new HashMap<>();
			cdcRecord.setDeltas(deltas);
			for (Field field : fields) {
				field.setAccessible(true);
				Object oValue = field.get(oldEntity);
				Object nValue = field.get(newEntity);

				Class<?> type = field.getType();
				if (type.isPrimitive() || isWrapperClass(type) || type.isEnum()) {
					if (!Objects.equals(oValue, nValue)) {
						log.info("Klass: {}, Field: {}, OValue: {}, NValue: {}", klass, field.getName(), oValue,
								nValue);
						deltas.put(field.getName(), Delta.of(oValue, nValue, type));
					}
				} else if (klass.getName().startsWith("com.as.batch")) {
					detectChanges(oValue, nValue, cdcRecords);
				} else if (klass.isArray()) {
					log.info("========== Array ==========");
				} else if (Collection.class.isAssignableFrom(klass)) {
					log.info("========== Collection ==========");
				} else if (Map.class.isAssignableFrom(klass)) {
					log.info("========== Map ==========");
				} else {
					log.warn("Unsupported: {} data type for comparison", type);
				}
			}
			cdcRecords.add(cdcRecord);

		} catch (Exception e) {
			throw new CdcException(e);
		}
	}

	private void readFieldsRecursively(Class<?> clazz, List<Field> fields) {
		log.info("Class: {}", clazz);
		if (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				fields.add(field);
			}
			readFieldsRecursively(clazz.getSuperclass(), fields);
		}
	}

	public boolean isWrapperClass(Class<?> type) {
		return type == Double.class || type == Float.class || type == Long.class || type == Integer.class
				|| type == Short.class || type == Character.class || type == Byte.class || type == Boolean.class
				|| type == String.class;
	}
}
