package com.universe.thirdparty.comparator.pojo;

import lombok.*;

import java.util.Comparator;

/**
 * @author Nick Liu
 * @date 2025/7/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hobby implements Comparable<Hobby>{

	private String name;
	private String desc;

	@Override
	public int compareTo(Hobby o) {
		return Comparator.comparing(Hobby::getName).thenComparing(Hobby::getDesc).compare(this, o);
	}
}
