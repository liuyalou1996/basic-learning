package com.universe.jdkapi.jdk21.patternMatch;

record Point(int x, int y) {

}

record Ellipse(Point center, double radius) {

}

public class RecordPatternMatchExample {

	public static void main(String[] args) {
		Object point = new Point(0, 0);
		Object ellipse = new Ellipse(new Point(1, 1), 5);
		print(point);
		print(ellipse);
	}

	public static void print(Object shape) {
		// Record类型对象在switch语句中支持解构
		String result = switch (shape) {
			case Point(int x, int y) -> STR."这是一个点，横坐标：\{x}，纵坐标：\{y}";
			case Ellipse(Point(int x, int y), double radius) -> STR."这是一个椭圆，横坐标：\{x}，纵坐标：\{y}，半径：\{radius}";
			default -> "Unknown Shape";
		};
		System.out.println(result);
	}
}
