package com.universe.thirdparty.comparator;

import com.google.common.collect.Lists;
import com.universe.thirdparty.comparator.core.*;
import com.universe.thirdparty.comparator.pojo.*;

import java.util.Date;
import java.util.List;

/**
 * @author Nick Liu
 * @date 2025/7/22
 */
public class AnnotatedComparatorExample {

	public static void main(String[] args) {
		Date date = new Date();
		UserDTO userDTO = new UserDTO("Nick", 28, date, "深圳", Lists.newArrayList(new Hobby("Sing", "唱歌")));

		Date date2 = new Date();
		UserDO userDO = new UserDO("Licky", 29, date2, null);

		ObjectComparator comparator = new AnnotatedFieldObjectComparator();
		List<ComparableField> fields = comparator.getDiffFields(userDTO, userDO);
		printResult(fields);

		List<Hobby> orgHobbies = Lists.newArrayList(new Hobby("Sing", "唱歌"), new Hobby("Dance", "跳舞"));
		List<Hobby> curHobbies = Lists.newArrayList(new Hobby("Dance", "跳舞"), new Hobby("Sing", "唱"));
		ComparableCollectionWrapper<Hobby> orgWrapper = new ComparableCollectionWrapper<>(orgHobbies);
		ComparableCollectionWrapper<Hobby> curWrapper = new ComparableCollectionWrapper<>(curHobbies);

		comparator = new FieldBasedObjectComparator();
		fields = comparator.getDiffFields(orgWrapper, curWrapper);
		printResult(fields);
	}

	private static void printResult(List<ComparableField> fields) {
		fields.forEach(field -> {
			System.out.println("======字段比较开始=====");
			System.out.println("字段名称:" + field.getFieldName());
			System.out.println("字段类型:" + field.getPreviousFieldType());
			System.out.println("修改前的值:" + field.getPreviousFieldValue());
			System.out.println("修改后的值:" + field.getCurrentFieldValue());
			System.out.println("======字段比较结束=====\n");
		});
	}
}
