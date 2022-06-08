package com.universe.algorithm.leetcode.simple;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/move-zeroes/
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作
 *
 * 示例 1:
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 *
 * @author 刘亚楼
 * @date 2022/6/8
 */
public class MoveZero {

	public void moveZeroes(int[] nums) {
		for (int index = nums.length - 1; index >= 0; index--) {
			if (nums[index] != 0) {
				continue;
			}
			for (int next = index; next < nums.length - 1; next++) {
				int temp = nums[next];
				nums[next] = nums[next + 1];
				nums[next + 1] = temp;
			}
		}
	}

	/**
	 * 使用双指针，左指针代表已处理好的序列的尾部，右指针指向待处理序列的头部。
	 * @param nums
	 */
	public void moveZeroesByDoublePointer(int[] nums) {
		for (int left = 0, right = 0; right < nums.length; right++) {
			if (nums[right] != 0) {
				int temp = nums[left];
				nums[left] = nums[right];
				nums[right] = temp;
				left++;
			}
		}
	}

	public static void main(String[] args) {
		int[] input = { 0, 1, 0, 3, 12 };

		MoveZero moveZero = new MoveZero();
		moveZero.moveZeroesByDoublePointer(input);
		System.out.println(Arrays.toString(input));
	}
}
