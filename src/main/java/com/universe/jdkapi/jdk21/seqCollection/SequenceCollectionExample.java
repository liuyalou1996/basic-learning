package com.universe.jdkapi.jdk21.seqCollection;

import java.util.LinkedHashSet;
import java.util.SequencedSet;

/**
 * @author Nick Liu
 * @date 2025/8/7
 */
public class SequenceCollectionExample {

	public static void main(String[] args) {
		SequencedSet<String> set = new LinkedHashSet<>();
		set.add("A");
		set.add("B");
		set.add("C");

		System.out.println("第一个元素：" + set.getFirst());
		System.out.println("最后一个元素：" + set.getLast());
		System.out.println("反转后的集合" + set.reversed());
	}
}