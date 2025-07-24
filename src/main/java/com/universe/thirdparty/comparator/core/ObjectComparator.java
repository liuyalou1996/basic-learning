package com.universe.thirdparty.comparator.core;

import java.util.List;

/**
 * @author Nick Liu
 * @date 2025/7/21
 */
public interface ObjectComparator {

	/**
	 * 两个对象是否全相等
	 * @param previous  之前的对象
	 * @param current 之后的对象
	 * @return 两个对象是否全相等
	 */
	boolean equals(Object previous, Object current);

	/**
	 * 获取不相等的属性
	 * @param previous  之前的对象
	 * @param current 之后的对象
	 * @return 不相等的属性，键为属性名，值为属性类型
	 */
	List<ComparableField> getDiffFields(Object previous, Object current);
}
