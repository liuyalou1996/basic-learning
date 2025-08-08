package com.universe.jdkapi.jdk21.sealed;

/**
 * @author Nick Liu
 * @date 2025/8/7
 */
public abstract sealed class Shape permits Circle, Rectangle, Triangle {

	public abstract double calculateArea();
}
