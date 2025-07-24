package com.universe.thirdparty.comparator.ext;

/**
 * @author Nick Liu
 * @date 2025/7/24
 */
public class DefaultFieldValueFormatter implements FieldValueFormatter<Object> {

	@Override
	public Object format(Object fieldValue) {
		return fieldValue;
	}
}
