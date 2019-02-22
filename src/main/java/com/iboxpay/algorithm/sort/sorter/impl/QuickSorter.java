package com.iboxpay.algorithm.sort.sorter.impl;

import com.iboxpay.algorithm.sort.sorter.Sorter;

/**
 * <p>原理：</p>
 * <p>选择一个关键值作为基准值。比基准值小的都在左边序列（一般是无序的），比基准值大的都在右边（一般是无序的），一般选择序列的第一个元素。</p>
 * 一次循环：从后往前比较，用基准值和最后一个值比较，如果比基准值小的交换位置，如果没有继续比较下一个，直到找到第一个比基准值小的值才交换。找到这个值之后，又从前往后开始比较，如果有比基准值大的，交换位置，如果没有继续比较下一个，直到找到第一个比基准值大的值才交换。直到从前往后的比较索引>从后往前比较的索引，结束第一次循环，此时，对于基准值来说，左右两边就是有序的了。接着分别比较左右两边的序列，重复上述的循环。
 */
public class QuickSorter implements Sorter {

  @Override
  public void sort(int[] arr) {
    sort(arr, 0, arr.length - 1);
  }

  private void sort(int[] arr, int startIndex, int endIndex) {
    // 左指针
    int left = startIndex;
    // 右指针
    int right = endIndex;
    // 基准值
    int key = arr[startIndex];
    int temp = 0;

    while (right > left) {
      // 从右往左找出比基准小的值，找到就交换
      while (right > left && arr[right] >= key) {
        right--;
      }

      if (arr[right] < key) {
        temp = arr[right];
        arr[right] = key;
        key = temp;
      }

      // 从左往右找出比基准值大的值，找到就交换
      while (right > left && arr[left] <= key) {
        left++;
      }

      if (arr[left] > key) {
        temp = arr[left];
        arr[left] = key;
        key = temp;
      }
    }

    // 多次循环后基准值左边的数都比基准值少，基准值右边的数都比基准值大，且左右两边指针值相等，等于基准值位置，但基准值左右两边的数据还需排序
    if (left > startIndex) {
      sort(arr, startIndex, left - 1);
    }

    if (right < endIndex) {
      sort(arr, right + 1, endIndex);
    }

  }

}
