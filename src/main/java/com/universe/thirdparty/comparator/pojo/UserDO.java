package com.universe.thirdparty.comparator.pojo;

import com.universe.thirdparty.comparator.annotation.FieldComparable;
import com.universe.thirdparty.comparator.annotation.FieldFormatter;
import com.universe.thirdparty.comparator.ext.DateFieldFormatter;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @author Nick Liu
 * @date 2025/7/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {

	@FieldComparable
	private String name;

	@FieldComparable
	private int age;

	@FieldComparable
	@FieldFormatter(valueFormatter = DateFieldFormatter.class)
	private Date date;

	private List<Hobby> hobbies;
}
