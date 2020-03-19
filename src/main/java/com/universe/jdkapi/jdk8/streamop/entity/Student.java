package com.universe.jdkapi.jdk8.streamop.entity;

import java.util.List;

/**
 * @author 刘亚楼
 * @date 2020/3/19
 */
public class Student {

	String name;
	Integer age;
	List<Hobby> hobbies;

	public Student(String name, Integer age, List<Hobby> hobbies) {
		this.name = name;
		this.age = age;
		this.hobbies = hobbies;
	}

	public String getName() {
		return name;
	}

	public List<Hobby> getHobbies() {
		return hobbies;
	}

	public Integer getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", hobbies=" + hobbies + "]";
	}
}
