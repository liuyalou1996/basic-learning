package com.universe.thirdparty.comparator.ext;

import com.universe.jdkapi.jdk8.datetime.DateTimeUtils;
import com.universe.jdkapi.jdk8.datetime.DateTimeUtils.Pattern;

import java.util.Date;

/**
 * @author Nick Liu
 * @date 2025/7/24
 */
public class DateFieldFormatter implements FieldValueFormatter<Date> {

	@Override
	public Object format(Date fieldValue) {
		return DateTimeUtils.format(fieldValue, Pattern.YEAR_TO_MILLIS_WITH_STRIKE);
	}
}
