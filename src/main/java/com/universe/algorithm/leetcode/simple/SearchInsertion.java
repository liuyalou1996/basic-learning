package com.universe.algorithm.leetcode.simple;

/**
 * @author 刘亚楼
 * @date 2022/6/4
 */
public class SearchInsertion {

	/**
	 * 暴力破解法，时间复杂度为O(n)
	 * @param input
	 * @param target
	 * @return
	 */
	public int search(int[] input, int target) {
		if (target < input[0]) {
			return 0;
		}
		for (int preIndex = 0, index = 0; index < input.length; index++) {
			if (input[index] == target) {
				return index;
			}
			if (target > input[preIndex] && target < input[index]) {
				return (preIndex + index) / 2 + 1;
			}
			preIndex = index;
		}
		return input.length;
	}

	/**
	 * 二分法时间复杂度更低
	 * @param input
	 * @param target
	 * @return
	 */
	public int searchV2(int[] input, int target) {
		int left = 0, right = input.length - 1, mid = 0;
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
		
		return target < input[mid] ? mid : mid + 1;
	}

	public static void main(String[] args) {
		int[] input = { 1, 3, 5, 6 };
		SearchInsertion searchInsertion = new SearchInsertion();
		System.out.println(searchInsertion.searchV2(input, 0));
	}
}
