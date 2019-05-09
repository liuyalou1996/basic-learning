package com.universe.algorithm.sort.sorter.impl;

import com.universe.algorithm.sort.sorter.Sorter;

/**
 * 针对直接插入排序的效率问题，对其进行改进，也就变成了希尔排序，因为插入排序每次只能讲数据移动一位
 */
public class ShellSorter implements Sorter {

  @Override
  public void sort(int[] arr) {
    // 进行分组
    int group = arr.length / 2;
    while (group != 0) {
      // 根据分组排序
      sortByGroup(arr, group);
      group /= 2;
    }
  }

  private void sortByGroup(int[] arr, int group) {
    for (int index = 0; index < group; index++) {
      // 从第二个数开始
      for (int i = index + group; i < arr.length; i += group) {
        // 从后往前比较
        for (int j = i; j > index; j -= group) {
          if (arr[j] < arr[j - group]) {
            int temp = arr[j];
            arr[j] = arr[j - group];
            arr[j - group] = temp;
          } else {
            // 左边数字已排序，如果当前数字比左边最大的数字还大则是最大数，无需交换
            break;
          }
        }
      }
    }
  }
}
