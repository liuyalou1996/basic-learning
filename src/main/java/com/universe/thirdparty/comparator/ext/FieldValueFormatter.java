package com.universe.thirdparty.comparator.ext;

/**
 * @author Nick Liu
 * @date 2025/7/24
 */
public interface FieldValueFormatter<T> {

	Object format(T fieldValue);
}
