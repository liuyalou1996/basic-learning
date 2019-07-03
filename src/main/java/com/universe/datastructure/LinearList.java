package com.universe.datastructure;

import java.util.Arrays;

/**
 * 线性表
 * @author liuyalou
 * @date 2019年7月3日
 */
public class LinearList {

  private static final int DEFAULT_CAPACITY = 10;

  private int capacity;

  private int size;

  private int[] array;

  public LinearList() {
    this.capacity = DEFAULT_CAPACITY;
    this.array = new int[DEFAULT_CAPACITY];
  }

  public LinearList(int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("illegal initialCapacity:" + initialCapacity);
    } else if (initialCapacity > Integer.MAX_VALUE) {
      this.capacity = Integer.MAX_VALUE;
      this.array = new int[Integer.MAX_VALUE];
    } else {
      this.capacity = initialCapacity;
      this.array = new int[initialCapacity];
    }
  }

  public int size() {
    return this.size;
  }

  public void add(int ele) {
    if (size >= capacity) {
      resize();
      this.array[size++] = ele;
    } else {
      this.array[size++] = ele;
    }
  }

  private void resize() {
    this.capacity = this.capacity * 2;
    int[] newArray = new int[this.capacity];
    newArray = Arrays.copyOf(this.array, this.capacity);
    this.array = newArray;
  }

  public void remove(int index) {
  }

}
