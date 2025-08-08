package com.universe.jdkapi.jdk21.strongEncapsulate;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * JDK 17加强了对JDK内部API的封装，使得外部代码无法访问这些API
 *
 * @author Nick Liu
 * @date 2025/8/7
 */
public class UnsafeExample {

	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		Field field = Unsafe.class.getDeclaredField("theUnsafe");
		field.setAccessible(true);
		Unsafe unsafe = (Unsafe) field.get(null);
		System.out.println(unsafe);
	}
}
