package com.universe.algorithm.leetcode.simple;

/**
 * https://leetcode.cn/problems/binary-search/
 * 二分查找
 *
 * 给定一个n个元素有序的（升序）整型数组nums 和一个目标值target ，写一个函数搜索nums中的 target，
 * 如果目标值存在返回下标，否则返回 -1。
 *
 * 输入: nums = [-1,0,3,5,9,12], target = 9
 * 输出: 4
 * 解释: 9 出现在 nums 中并且下标为 4
 * @author 刘亚楼
 * @date 2022/6/4
 */
public class BinarySearch {

	public int search(int[] input, int target) {
		int left = 0, right = input.length - 1, mid;
		while (left <= right) {
			mid = (left + right) / 2;
			if (input[mid] == target) {
				return mid;
			}
			if (target < input[mid]) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] input = { -1, 0, 3, 5, 9, 12 };
		BinarySearch binarySearch = new BinarySearch();
		int index = binarySearch.search(input, 12);
		System.out.println(index);
	}
}
