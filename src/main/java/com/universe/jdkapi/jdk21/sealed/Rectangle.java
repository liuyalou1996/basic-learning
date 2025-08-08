package com.universe.jdkapi.jdk21.sealed;

import lombok.Data;

/**
 * @author Nick Liu
 * @date 2025/8/7
 */
@Data
public non-sealed class Rectangle extends Shape {

	private final double width;
	private final double height;

	@Override
	public double calculateArea() {
		return width * height;
	}
}
