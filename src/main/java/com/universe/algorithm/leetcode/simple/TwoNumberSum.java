package com.universe.algorithm.leetcode.simple;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/two-sum/
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 示例 1：
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * @author 刘亚楼
 * @date 2022/6/3
 */
public class TwoNumberSum {

	public int[] calculate(int[] nums, int target) {
		int[] result = new int[2];

		for (int index = 0; index < nums.length; index++) {
			for (int next = index + 1; next < nums.length; next++) {
				if (nums[index] + nums[next] == target) {
					result[0] = index;
					result[1] = next;
					break;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int[] nums = new int[] { 2, 7, 11, 15 };
		int target = 9;

		TwoNumberSum twoNumberSum = new TwoNumberSum();
		System.out.println(Arrays.toString(twoNumberSum.calculate(nums, 9)));
	}
}
