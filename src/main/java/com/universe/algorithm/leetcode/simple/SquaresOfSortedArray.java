package com.universe.algorithm.leetcode.simple;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/squares-of-a-sorted-array/
 * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
 *
 * 输入：nums = [-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 *
 * 输入：nums = [-7,-3,2,3,11]
 * 输出：[4,9,9,49,121]
 * @author 刘亚楼
 * @date 2022/6/7
 */
public class SquaresOfSortedArray {

	public int[] sortedSquares(int[] nums) {
		for (int index = 0; index < nums.length; index++) {
			nums[index] = nums[index] * nums[index];
		}

		for (int count = 0; count < nums.length - 1; count++) {
			for (int lastPointer = count + 1; lastPointer > 0; lastPointer--) {
				if (nums[lastPointer] < nums[lastPointer - 1]) {
					int temp = nums[lastPointer];
					nums[lastPointer] = nums[lastPointer - 1];
					nums[lastPointer - 1] = temp;
				} else {
					break;
				}
			}
		}

		return nums;
	}

	public static void main(String[] args) {
		SquaresOfSortedArray squaresOfSortedArray = new SquaresOfSortedArray();
		int[] result = squaresOfSortedArray.sortedSquares(new int[] { -7, -3, 2, 3, 11 });
		System.out.println(Arrays.toString(result));
	}
}
