package com.iboxpay.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

import com.iboxpay.algorithm.sort.sorter.Sorter;
import com.iboxpay.algorithm.sort.sorter.impl.BubbleSorter;
import com.iboxpay.algorithm.sort.sorter.impl.SelectionSorter;

public class SortTest {

  public static int[] initArray() {
    int[] arr = new int[10];
    Random rd = new Random();
    for (int count = 0; count < arr.length; count++) {
      arr[count] = rd.nextInt(10) + 1;
    }

    return arr;
  }

  public static void selectionSortTest() {
    int[] arr = initArray();
    Sorter selectionSorter = new SelectionSorter();
    selectionSorter.sort(arr);
    System.out.println(Arrays.toString(arr));
  }

  public static void bubbleSortTest() {
    int[] arr = initArray();
    Sorter bubbleSorter = new BubbleSorter();
    bubbleSorter.sort(arr);
    System.out.println(Arrays.toString(arr));
  }

  public static void main(String[] args) {
    System.out.println("选择排序测试!");
    selectionSortTest();
    System.out.println("冒泡排序测试!");
    bubbleSortTest();
  }

}
