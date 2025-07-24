package com.universe.thirdparty.comparator.core;

import lombok.*;

import java.lang.reflect.Field;

/**
 * @author Nick Liu
 * @date 2025/7/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComparableField {
	/**
	 * 字段名称
	 */
	private String fieldName;
	/**
	 * 之前字段的类型
	 */
	private Class<?> previousFieldType;
	/**
	 * 当前字段的类型
	 */
	private Class<?> currentFieldType;
	/**
	 * 之前对象字段值
	 */
	private Object previousFieldValue;
	/**
	 * 当前对象字段值
	 */
	private Object currentFieldValue;
	/**
	 * 之前字段
	 */
	private Field previousField;
	/**
	 * 当前字段
	 */
	private Field currentField;
}
