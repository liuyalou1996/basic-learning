package com.universe.jdkapi.jdk8.streamop.entity;

/**
 * @author 刘亚楼
 * @date 2020/3/19
 */
public class Hobby {

	String hobbyName;

	public Hobby(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	public String getHobbyName() {
		return hobbyName;
	}

	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	@Override
	public String toString() {
		return "Hobby [hobbyName=" + hobbyName + "]";
	}
}
