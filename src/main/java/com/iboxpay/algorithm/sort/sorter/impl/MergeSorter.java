package com.iboxpay.algorithm.sort.sorter.impl;

import com.iboxpay.algorithm.sort.sorter.Sorter;

public class MergeSorter implements Sorter {

  @Override
  public void sort(int[] arr) {
    sort(arr, 0, arr.length - 1);
  }

  private static void sort(int[] arr, int startIndex, int endIndex) {
    int mid = (startIndex + endIndex) / 2;
    // 至少有两个或两个以上元素才进行分割，分而治之
    if (startIndex < endIndex) {
      sort(arr, startIndex, mid);
      sort(arr, mid + 1, endIndex);
      merge(arr, startIndex, mid, endIndex);
    }
  }

  private static void merge(int[] arr, int startIndex, int mid, int endIndex) {
    int left = startIndex;
    int right = mid + 1;
    int count = 0;
    // 临时数组
    int[] temp = new int[endIndex - startIndex + 1];

    // 从两组中选出数值小的存放到临时数组中
    while (left <= mid && right <= endIndex) {
      if (arr[left] < arr[right]) {
        temp[count++] = arr[left++];
      } else {
        temp[count++] = arr[right++];
      }
    }

    // 如果左边组还有数据
    while (left <= mid) {
      temp[count++] = arr[left++];
    }

    // 如果右边组还有数据
    while (right <= endIndex) {
      temp[count++] = arr[right++];
    }

    // 左右两边数据进行合并
    for (count = 0; count < temp.length; count++) {
      arr[startIndex + count] = temp[count];
    }
  }

}
