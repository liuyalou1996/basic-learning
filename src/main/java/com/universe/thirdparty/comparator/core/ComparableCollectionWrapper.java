package com.universe.thirdparty.comparator.core;

import com.universe.thirdparty.comparator.annotation.FieldComparable;
import lombok.Data;

import java.util.*;

/**
 * @author Nick Liu
 * @date 2025/7/22
 */
@Data
public class ComparableCollectionWrapper<T> {

	@FieldComparable
	private Collection<T> collection;

	/**
	 * 集合中比较的对象必须实现Comparable接口
	 * @param collection
	 */
	public ComparableCollectionWrapper(Collection<T> collection) {
		this.collection = new TreeSet<>(collection);
	}

	public ComparableCollectionWrapper(Collection<T> collection, Comparator<T> comparator) {
		Set<T> set = new TreeSet<>(comparator);
		set.addAll(collection);
		this.collection = set;
	}
}
