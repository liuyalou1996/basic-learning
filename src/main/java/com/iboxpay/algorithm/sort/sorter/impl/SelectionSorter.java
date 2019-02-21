package com.iboxpay.algorithm.sort.sorter.impl;

import com.iboxpay.algorithm.sort.sorter.Sorter;

public class SelectionSorter implements Sorter {

  @Override
  public void sort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[i] > arr[j]) {
          int temp = arr[i];
          arr[i] = arr[j];
          arr[j] = temp;
        }
      }
    }
  }
}
