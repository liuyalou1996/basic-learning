package com.universe.jdkapi.jdk21.sealed;

import lombok.Data;

/**
 * @author Nick Liu
 * @date 2025/8/7
 */
@Data
public final class Triangle extends Shape {

	private final double base;
	private final double height;

	@Override
	public double calculateArea() {
		return (base * height) / 2;
	}
}
