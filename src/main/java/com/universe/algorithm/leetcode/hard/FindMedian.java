package com.universe.algorithm.leetcode.hard;

/**
 * https://leetcode.cn/problems/median-of-two-sorted-arrays/
 *
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组nums1 和nums2。请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 *
 * @author 刘亚楼
 * @date 2022/6/4
 */
public class FindMedian {

	public double findMedianFromTwoSortedArrays(int[] firstArray, int[] secondArray) {
		int firstIndex = 0, secondIndex = 0, mergedIndex = 0;
		int firstLength = firstArray.length, secondLength = secondArray.length;
		int mergedLength = firstLength + secondLength;
		int[] mergedArray = new int[mergedLength];

		// 先合并两个有序数组
		while (firstIndex < firstLength || secondIndex < secondLength) {

			while (firstIndex < firstLength) {
				// 这里要注意数组为空的情况
				if (secondLength > 0 && firstArray[firstIndex] > secondArray[secondIndex]) {
					break;
				}
				mergedArray[mergedIndex++] = firstArray[firstIndex++];
			}

			if (firstIndex == firstLength) {
				while (mergedIndex < mergedLength) {
					mergedArray[mergedIndex++] = secondArray[secondIndex++];
				}
				break;
			}

			while (secondIndex < secondLength) {
				// 这里要注意数组为空的情况
				if (firstLength > 0 && secondArray[secondIndex] > firstArray[firstIndex]) {
					break;
				}
				mergedArray[mergedIndex++] = secondArray[secondIndex++];
			}

			if (secondIndex == secondLength) {
				while (mergedIndex < mergedLength) {
					mergedArray[mergedIndex++] = firstArray[firstIndex]++;
				}
				break;
			}
		}

		int middle = (mergedLength - 1) / 2;
		int mod = mergedLength % 2;
		return mod == 0 ? (mergedArray[middle] + mergedArray[middle + 1]) / 2D : mergedArray[middle];
	}

	public static void main(String[] args) {
		int[] first = { 1, 3, };
		int[] second = { 2, 4, 6, 7, 8, 9, 10 };

		FindMedian findMedian = new FindMedian();
		double median = findMedian.findMedianFromTwoSortedArrays(first, second);
		System.out.println(median);
	}
}
