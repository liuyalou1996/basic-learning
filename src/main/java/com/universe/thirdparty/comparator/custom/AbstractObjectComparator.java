package com.universe.thirdparty.comparator.custom;

import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Nick Liu
 * @date 2025/7/22
 */
@Setter
public abstract class AbstractObjectComparator implements ObjectComparator {

	private static final List<Class<?>> WRAPPER = Arrays.asList(Byte.class,
		Short.class,
		Integer.class,
		Long.class,
		Float.class,
		Double.class,
		Character.class,
		Boolean.class,
		String.class);

	/**
	 * 只有字段在两个对象中都存在才比较
	 */
	private boolean compareOnBothExisted = true;

	/**
	 * 包含的字段
	 */
	private List<String> includedFields;
	/**
	 * 排除的字段
	 */
	private List<String> excludedFields;

	public AbstractObjectComparator() {
		includedFields = Collections.emptyList();
		excludedFields = Collections.emptyList();
	}

	public AbstractObjectComparator(boolean compareOnBothExisted) {
		this.compareOnBothExisted = compareOnBothExisted;
	}

	public AbstractObjectComparator(boolean compareOnBothExisted, List<String> includeFields, List<String> excludeFields) {
		this.compareOnBothExisted = compareOnBothExisted;
		this.includedFields = includeFields;
		this.excludedFields = excludeFields;
	}

	/**
	 * 指定包含或排除某些字段
	 *
	 * @param includeFields 包含字段，若为 null 或空集，则不指定
	 * @param excludeFields 排除字段，若为 null 或空集，则不指定
	 */
	public AbstractObjectComparator(List<String> includeFields, List<String> excludeFields) {
		this.includedFields = includeFields;
		this.excludedFields = excludeFields;
	}

	/**
	 * 只要没有不相等的属性，两个对象就全相等
	 *
	 * @param previous  对象1
	 * @param current 对象2
	 * @return 两个对象是否全相等
	 */
	@Override
	public boolean equals(Object previous, Object current) {
		List<ComparableField> diff = getDiffFields(previous, current);
		return diff == null || diff.isEmpty();
	}

	/**
	 * 对比两个对象的指定属性是否相等，默认为两个对象是否 equals
	 * <p>
	 * 子类可以通过覆盖此方法对某些特殊属性进行比对
	 *
	 * @param comparableField 当前比对属性信息
	 * @return 属性是否相等
	 */
	protected boolean isFieldEqual(ComparableField comparableField) {
		// 先判断排除，如果需要排除，则无论在不在包含范围，都一律不比对
		if (isExcluded(comparableField)) {
			return true;
		}
		// 如果有指定需要包含的字段而且当前字段不在需要包含的字段中则不比对
		if (!isIncluded(comparableField)) {
			return true;
		}
		return nullableEquals(comparableField.getPreviousFieldValue(), comparableField.getCurrentFieldValue());
	}

	/**
	 * 确定是否需要比较这个字段，子类可以扩展这个方法，自定义判断方式
	 */
	protected boolean isIncluded(ComparableField comparableField) {
		// 没有指定需要包含的字段，则全部都包含
		if (includedFields == null || includedFields.isEmpty()) {
			return true;
		}
		return includedFields.contains(comparableField.getFieldName());
	}

	/**
	 * 确定是否需要需要排除这个字段，子类可以扩展这个方法，自定义判断方式
	 */
	protected boolean isExcluded(ComparableField comparableField) {
		// 如果有指定需要排除的字段，而且当前字段是需要排除字段，则直接返回 true
		return excludedFields != null && !excludedFields.isEmpty() && excludedFields.contains(comparableField.getFieldName());
	}

	/**
	 * 如果简单数据类型的对象则直接进行比对
	 *
	 * @param first  对象1
	 * @param second 对象2
	 * @return 不同的字段信息，相等返回空集，不等则 FieldInfo 的字段名为对象的类型名称
	 */
	protected List<ComparableField> compareSimpleField(Object first, Object second) {
		if (Objects.equals(first, second)) {
			return Collections.emptyList();
		}

		Object obj = first == null ? second : first;
		Class<?> clazz = obj.getClass();
		// 不等的字段名称使用类的名称
		ComparableField field = new ComparableField();
		field.setFieldName(clazz.getSimpleName());
		field.setPreviousFieldType(clazz);
		field.setCurrentFieldType(clazz);
		field.setPreviousFieldValue(first);
		field.setCurrentFieldValue(second);
		return Collections.singletonList(field);
	}

	/**
	 * 判断是否为原始数据类型
	 *
	 * @param first  对象1
	 * @param second 对象2
	 * @return 是否为原始数据类型
	 */
	protected boolean isSimpleField(Object first, Object second) {
		Object obj = first == null ? second : first;
		Class<?> clazz = obj.getClass();
		return clazz.isPrimitive() || WRAPPER.contains(clazz);
	}

	/**
	 * 只比对共同字段
	 */
	protected Set<String> getAllFieldNames(Set<String> firstFields, Set<String> secondFields) {
		Set<String> allFields;
		// 只取交集
		if (this.compareOnBothExisted) {
			allFields = firstFields.stream().filter(secondFields::contains).collect(Collectors.toSet());
		} else {
			// 否则取并集
			allFields = new HashSet<>(firstFields);
			allFields.addAll(secondFields);
		}
		return allFields;
	}

	private boolean nullableEquals(Object first, Object second) {
		if (first instanceof Collection && second instanceof Collection) {
			// 如果两个都是集合类型，尝试转换为数组再进行深度比较
			return Objects.deepEquals(((Collection<?>) first).toArray(), ((Collection<?>) second).toArray());
		}
		return Objects.deepEquals(first, second);
	}
}
