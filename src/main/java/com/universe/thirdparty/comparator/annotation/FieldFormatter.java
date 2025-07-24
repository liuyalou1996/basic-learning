package com.universe.thirdparty.comparator.annotation;

import com.universe.thirdparty.comparator.ext.*;

import java.lang.annotation.*;

/**
 * @author Nick Liu
 * @date 2025/7/18
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldFormatter {

	/**
	 * 字段名格式化器
	 * @return
	 */
	Class<? extends FieldNameFormatter> nameFormatter() default DefaultFieldNameFormatter.class;

	/**
	 * 字段值格式化器
	 * @return
	 */
	Class<? extends FieldValueFormatter> valueFormatter() default DefaultFieldValueFormatter.class;
}
