package com.universe.algorithm.leetcode.simple;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/merge-sorted-array/
 * 给你两个按 非递减顺序 排列的整数数组nums1 和 nums2，另有两个整数 m 和 n ，
 * 分别表示 nums1 和 nums2 中的元素数目。
 *
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 *
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
 *
 * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
 * 输出：[1]
 * 解释：需要合并 [1] 和 [] 。
 * 合并结果是 [1] 。
 * @author 刘亚楼
 * @date 2022/6/5
 */
public class MergeTwoSortedArray {

	/**
	 * 直接从后往前插入元素
	 * @param arr1
	 * @param m
	 * @param arr2
	 * @param n
	 */
	public void merge(int[] arr1, int m, int[] arr2, int n) {
		int lastPointerOfArr1 = m - 1, lastPointerOfArr2 = n - 1, mergedLastPointer = m + n - 1;

		int lastElement = 0;
		while (lastPointerOfArr1 >= 0 || lastPointerOfArr2 >= 0) {
			if (lastPointerOfArr1 == -1) {
				lastElement = arr2[lastPointerOfArr2--];
			} else if (lastPointerOfArr2 == -1) {
				lastElement = arr1[lastPointerOfArr1--];
			} else if (arr1[lastPointerOfArr1] > arr2[lastPointerOfArr2]) {
				lastElement = arr1[lastPointerOfArr1--];
			} else {
				lastElement = arr2[lastPointerOfArr2--];
			}
			arr1[mergedLastPointer--] = lastElement;
		}

	}

	public static void main(String[] args) {
		MergeTwoSortedArray mergeTwoSortedArray = new MergeTwoSortedArray();
		int[] arr1 = { 1, 2, 3, 0, 0, 0 }, arr2 = { 2, 5, 6 };
		mergeTwoSortedArray.merge(arr1, 3, arr2, 3);

		System.out.println(Arrays.toString(arr1));
	}
}
