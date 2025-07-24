package com.universe.thirdparty.comparator.core;

import com.universe.thirdparty.comparator.annotation.FieldFormatter;
import com.universe.thirdparty.comparator.ext.FieldNameFormatter;
import com.universe.thirdparty.comparator.ext.FieldValueFormatter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Nick Liu
 * @date 2025/7/21
 */
public class FieldBasedObjectComparator extends AbstractObjectComparator {

	private static final Map<Class<?>, Map<String, Field>> CACHE = new ConcurrentHashMap<>();

	@Override
	public List<ComparableField> getDiffFields(Object previous, Object current) {
		if (previous == current) {
			return Collections.emptyList();
		}
		// 先尝试判断是否为简单数据类型
		if (isSimpleField(previous, current)) {
			return compareSimpleField(previous, current);
		}
		Set<String> allFieldNames;
		// 获取所有字段
		Map<String, Field> previousFields = getAllFields(previous);
		Map<String, Field> currentFields = getAllFields(current);
		if (previous == null) {
			allFieldNames = currentFields.keySet();
		} else if (current == null) {
			allFieldNames = previousFields.keySet();
		} else {
			allFieldNames = getAllFieldNames(previousFields.keySet(), currentFields.keySet());
		}
		List<ComparableField> diffFields = new ArrayList<>();
		for (String fieldName : allFieldNames) {
			try {
				Field previousField = previousFields.getOrDefault(fieldName, null);
				Field currentField = currentFields.getOrDefault(fieldName, null);
				Class<?> previousType = null;
				Class<?> currentType = null;
				Object previousVal = null;
				Object currentVal = null;
				Class<? extends FieldNameFormatter> preNameFormatterType = null;
				Class<? extends FieldValueFormatter> preValueFormatterType = null;
				Class<? extends FieldNameFormatter> curNameFormatterType = null;
				Class<? extends FieldValueFormatter> curValueFormatterType = null;
				if (previousField != null) {
					previousField.setAccessible(true);
					previousVal = previousField.get(previous);
					previousType = previousField.getType();
					if (previousField.isAnnotationPresent(FieldFormatter.class)) {
						FieldFormatter fieldFormatter = previousField.getAnnotation(FieldFormatter.class);
						preNameFormatterType = fieldFormatter.nameFormatter();
						preValueFormatterType = fieldFormatter.valueFormatter();
					}
				}
				if (currentField != null) {
					currentField.setAccessible(true);
					currentVal = currentField.get(current);
					currentType = currentField.getType();
					if (currentField.isAnnotationPresent(FieldFormatter.class)) {
						FieldFormatter fieldFormatter = currentField.getAnnotation(FieldFormatter.class);
						curNameFormatterType = fieldFormatter.nameFormatter();
						curValueFormatterType = fieldFormatter.valueFormatter();
					}
				}

				ComparableField field = new ComparableField();
				field.setFieldName(fieldName);
				field.setPreviousFieldType(previousType);
				field.setCurrentFieldType(currentType);
				field.setPreviousFieldValue(previousVal);
				field.setCurrentFieldValue(currentVal);
				field.setPreviousField(previousField);
				field.setCurrentField(currentField);
				if (!isFieldEqual(field)) {
					diffFields.add(field);
				}
				Class<? extends FieldNameFormatter> nameFormatterType = preNameFormatterType == null ? curNameFormatterType : preNameFormatterType;
				if (nameFormatterType != null) {
					FieldNameFormatter fieldNameFormatter = nameFormatterType.newInstance();
					field.setFieldName(fieldNameFormatter.format(fieldName));
				}
				if (preValueFormatterType != null) {
					FieldValueFormatter previousValueFormatter = preValueFormatterType.newInstance();
					field.setPreviousFieldValue(previousValueFormatter.format(previousVal));
				}
				if (curValueFormatterType != null) {
					FieldValueFormatter currentValueFormatter = curValueFormatterType.newInstance();
					field.setCurrentFieldValue(currentValueFormatter.format(currentVal));
				}
			} catch (Exception e) {
				throw new IllegalStateException("Unable to compare field: " + fieldName, e);
			}
		}
		return diffFields;
	}

	private Map<String, Field> getAllFields(Object obj) {
		if (obj == null) {
			return Collections.emptyMap();
		}
		return CACHE.computeIfAbsent(obj.getClass(), k -> {
			Map<String, Field> fieldMap = new HashMap<>(8);
			Class<?> cls = k;
			while (cls != Object.class) {
				Field[] fields = cls.getDeclaredFields();
				for (Field field : fields) {
					// 一些通过字节码注入改写类的框架会合成一些字段，如 jacoco 的 $jacocoData 字段
					// 正常情况下这些字段都需要被排除掉
					if (!field.isSynthetic()) {
						fieldMap.put(field.getName(), field);
					}
				}
				cls = cls.getSuperclass();
			}
			return fieldMap;
		});
	}
}
