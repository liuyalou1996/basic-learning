package com.universe.jdkapi.jdk21.patternMatch;

import com.universe.jdkapi.jdk21.sealed.*;

/**
 * @author Nick Liu
 * @date 2025/8/7
 */
public class PatternMatchExample {

	public static void main(String[] args) {
		Shape circle = new Circle(5);
		Shape rectangle = new Rectangle(5, 2);
		System.out.println(STR."圆的周长为: \{calPerimeter(circle)}");
		System.out.println(STR."长方形的周长为：: \{calPerimeter(rectangle)}");
	}

	public static double calPerimeter(Shape shape) {
		return switch (shape) {
			case Circle v -> 2 * Math.PI * v.getRadius();
			case Rectangle v -> v.getWidth() + v.getHeight();
			default -> throw new IllegalArgumentException("Invalid shape");
		};
	}

}
