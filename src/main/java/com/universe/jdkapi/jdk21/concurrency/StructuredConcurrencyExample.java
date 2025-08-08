package com.universe.jdkapi.jdk21.concurrency;

import com.universe.jdkapi.jdk21.sealed.Circle;
import com.universe.jdkapi.jdk21.sealed.Triangle;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope.ShutdownOnFailure;
import java.util.concurrent.StructuredTaskScope.Subtask;

/**
 * @author Nick Liu
 * @date 2025/8/8
 */
public class StructuredConcurrencyExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		try (var scope = new ShutdownOnFailure()) {
			Subtask<Double> circleAreaTask = scope.fork(() -> calCircleArea(2));
			Subtask<Double> triangleAreaTask = scope.fork(() -> calTriangleArea(5, 5));

			// 阻塞等待所有子任务完成
			scope.join();
			// 其中任何一个子任务失败则抛出异常
			scope.throwIfFailed();

			double circleArea = circleAreaTask.get();
			double triangleArea = triangleAreaTask.get();
			System.out.println(STR."圆的面积为：\{circleArea}");
			System.out.println(STR."三角形面积为：\{triangleArea}");
		}
	}

	public static double calCircleArea(double radius) {
		return new Circle(radius).calculateArea();
	}

	public static double calTriangleArea(double base, double height) {
		return new Triangle(base, height).calculateArea();
	}

}
