package com.universe.jdkapi.jdk21.sealed;

import lombok.Data;

/**
 * @author Nick Liu
 * @date 2025/8/7
 */
@Data
public final class Circle extends Shape {

	private final double radius;

	@Override
	public double calculateArea() {
		return Math.PI * Math.pow(this.radius, 2);
	}
}
