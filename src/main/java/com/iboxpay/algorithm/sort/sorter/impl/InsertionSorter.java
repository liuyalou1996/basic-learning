package com.iboxpay.algorithm.sort.sorter.impl;

import com.iboxpay.algorithm.sort.sorter.Sorter;

/**
 * <p>插入排序是简单排序中最快的排序算法，虽然时间复杂度仍然为O(n*n)，但是却比冒泡排序和选择排序快很多。</p>
 * 原理：<br/>
 * 1、将指针指向某个元素，假设该元素左侧的元素全部有序，将该元素抽取出来，然后按照从右往左的顺序分别与其左边的元素比较，遇到比其大的元素便将元素右移，直到找到比该元素小的元素或者找到最左面发现其左侧的元素都比它大，停止。<br/>
 * 2、此时会出现一个空位，将该元素放入到空位中，此时该元素左侧的元素都比它小，右侧的元素都比它大。<br/>
 * 3、指针向后移动一位，重复上述过程。每操作一轮，左侧有序元素都增加一个，右侧无序元素都减少一个。
 */
public class InsertionSorter implements Sorter {

  @Override
  public void sort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      for (int j = i; j > 0; j--) {
        if (arr[j] < arr[j - 1]) {
          int temp = arr[j];
          arr[j] = arr[j - 1];
          arr[j - 1] = temp;
        }
      }
    }
  }
}
