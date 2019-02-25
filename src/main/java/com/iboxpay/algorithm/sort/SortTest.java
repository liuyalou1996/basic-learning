package com.iboxpay.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

import com.iboxpay.algorithm.sort.sorter.Sorter;
import com.iboxpay.algorithm.sort.sorter.impl.BubbleSorter;
import com.iboxpay.algorithm.sort.sorter.impl.InsertionSorter;
import com.iboxpay.algorithm.sort.sorter.impl.MergeSorter;
import com.iboxpay.algorithm.sort.sorter.impl.QuickSorter;
import com.iboxpay.algorithm.sort.sorter.impl.SelectionSorter;
import com.iboxpay.algorithm.sort.sorter.impl.ShellSorter;

public class SortTest {

  public static int[] initArray() {
    int[] arr = new int[10];
    Random rd = new Random();
    for (int count = 0; count < arr.length; count++) {
      arr[count] = rd.nextInt(100) + 1;
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

  public static void insertionSortTest() {
    int[] arr = initArray();
    Sorter insertionSorter = new InsertionSorter();
    insertionSorter.sort(arr);
    System.out.println(Arrays.toString(arr));
  }

  public static void shellSortTest() {
    int[] arr = initArray();
    Sorter shellSorter = new ShellSorter();
    shellSorter.sort(arr);
    System.out.println(Arrays.toString(arr));
  }

  public static void quickSortTest() {
    int[] arr = initArray();
    Sorter quickSorter = new QuickSorter();
    quickSorter.sort(arr);
    System.out.println(Arrays.toString(arr));
  }

  public static void mergeSort() {
    int[] arr = initArray();
    Sorter mergeSorter = new MergeSorter();
    mergeSorter.sort(arr);
    System.out.println(Arrays.toString(arr));
  }

  public static void main(String[] args) {
    System.out.println("选择排序测试!");
    selectionSortTest();
    System.out.println("冒泡排序测试!");
    bubbleSortTest();
    System.out.println("插入排序测试!");
    insertionSortTest();
    System.out.println("希尔排序测试!");
    shellSortTest();
    System.out.println("快速排序测试!");
    quickSortTest();
    System.out.println("归并排序测试!");
    mergeSort();
  }

}
