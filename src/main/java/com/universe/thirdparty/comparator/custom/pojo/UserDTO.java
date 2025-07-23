package com.universe.thirdparty.comparator.custom.pojo;

import com.universe.thirdparty.comparator.FieldComparable;
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
public class UserDTO {

	@FieldComparable
	private String name;
	private int age;
	private Date date;
	private String address;
	private List<Hobby> hobbies;
}
