package com.universe.thirdparty.comparator.custom;

import com.universe.thirdparty.comparator.FieldComparable;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * 基于注解的对象比较器
 * @author Nick Liu
 * @date 2025/7/22
 */
public class AnnotatedFieldObjectComparator extends FieldBasedObjectComparator {

	@Override
	protected boolean isIncluded(ComparableField comparableField) {
		Field fistField = comparableField.getPreviousField();
		Field secondField = comparableField.getCurrentField();
		boolean isFirstComparablePresent = Optional.ofNullable(fistField).map(f -> f.isAnnotationPresent(FieldComparable.class)).orElse(false);
		boolean isSecondComparablePresent = Optional.ofNullable(secondField).map(f -> f.isAnnotationPresent(FieldComparable.class)).orElse(false);
		return super.isIncluded(comparableField) || (isFirstComparablePresent && isSecondComparablePresent);
	}
}
