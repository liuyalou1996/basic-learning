package com.universe.algorithm.leetcode.middle;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/rotate-array/
 *
 * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 *
 * 【示例1】
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 *
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]
 *
 * @author 刘亚楼
 * @date 2022/6/8
 */
public class RotateArray {

	/**
	 * 以length=7, k=3为例，原数组为：[1,2,3,4,5,6,7]
	 *
	 * 解法：
	 * 1、先翻转所有元素，如：[7,6,5,4,3,2,1]
	 * 2、再翻转[0,k % length]范围内的元素，如：[5,6,7,4,3,2,1]
	 * 3、再翻转[k % length, length-1]范围内的元素，如：[5,6,7,1,2,3,4]
	 * @param nums
	 * @param k
	 */
	public void rotate(int[] nums, int k) {
		int mod = k % nums.length;

		reverse(nums, 0, nums.length - 1);
		reverse(nums, 0, mod - 1);
		reverse(nums, mod, nums.length - 1);
	}

	private void reverse(int[] nums, int start, int end) {
		while (start < end) {
			int temp = nums[start];
			nums[start] = nums[end];
			nums[end] = temp;
			start++;
			end--;
		}
	}

	public static void main(String[] args) {
		RotateArray rotateArray = new RotateArray();
		int[] input = { 1, 2, 3, 4, 5, 6, 7 };
		rotateArray.rotate(input, 3);
		System.out.println(Arrays.toString(input));
	}
}
