package com.iboxpay.algorithm.sort.sorter.impl;

import com.iboxpay.algorithm.sort.sorter.Sorter;

/**
 * <p>原理：<p>
 * 1、从第一个数据开始，与第二个数据相比较，如果第二个数据小于第一个数据，则交换两个数据的位置。<br/>
 * 2、指针由第一个数据移向第二个数据，第二个数据与第三个数据相比较，如果第三个数据小于第二个数据，则交换两个数据的位置。<br/>
 * 3、依此类推，完成第一轮排序。第一轮排序结束后，最大的元素被移到了最右面。<br/>
 * 4、依照上面的过程进行第二轮排序，将第二大的排在倒数第二的位置。<br/>
 * 5、重复上述过程，没排完一轮，比较次数就减少一次。
 */
public class BubbleSorter implements Sorter {

  @Override
  public void sort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = 0; j < arr.length - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
  }
}
