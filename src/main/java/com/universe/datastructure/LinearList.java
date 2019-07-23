package com.universe.datastructure;

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
    capacity = DEFAULT_CAPACITY;
    array = new int[DEFAULT_CAPACITY];
  }

  public LinearList(int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("Illegal initialCapacity:" + initialCapacity);
    } else if (initialCapacity > Integer.MAX_VALUE) {
      capacity = Integer.MAX_VALUE;
    } else {
      capacity = initialCapacity;
    }

    array = new int[capacity];
  }

  public int size() {
    return size;
  }

  public void add(int ele) {
    if (size >= capacity) {
      resize();
    }

    array[size++] = ele;
  }

  private void resize() {
    capacity = capacity * 2;
    int[] newArray = new int[capacity];
    System.arraycopy(array, 0, newArray, 0, size);
    array = newArray;
  }

  public void remove(int index) {
    if (index >= size) {
      throw new IndexOutOfBoundsException("Index:" + index + ",Size:" + size);
    }

    int movingCount = size - index - 1;
    if (movingCount > 0) {
      System.arraycopy(array, index + 1, array, index, size - index - 1);
    }

    array[--size] = 0;
  }

  @Override
  public String toString() {
    StringBuilder toStringBuilder = new StringBuilder("LinearList[");
    for (int index = 0; index < size; index++) {
      if (index < size - 1) {
        toStringBuilder.append(array[index]).append(",");
      } else {
        toStringBuilder.append(array[index]).append("]");
      }
    }

    return toStringBuilder.toString();
  }
}
