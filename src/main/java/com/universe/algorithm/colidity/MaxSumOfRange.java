package com.universe.algorithm.colidity;

/**
 * 数组中相同两个元素间所有元素和最大值
 * 示例1：
 * 输入：[1,1,2,3,6,6,2,2,6,9,9]
 * 输出：22
 *
 * 示例2：
 * 输入：[5,1,4,1,6]
 * 输出：6
 *
 * 示例：3
 * 输入：[5,1,4,6]
 * 输出：-1
 * @author 刘亚楼
 * @date 2022/6/6
 */
public class MaxSumOfRange {

	public int solution(int[] A) {
		int sum, maxSum = -1;
		boolean addable;

		for (int index = 0; index < A.length - 1; index++) {
			sum = 0;
			addable = false;
			sum += A[index];
			for (int last = A.length - 1; last > index; last--) {
				if (A[index] == A[last]) {
					addable = true;
				}
				if (addable) {
					sum += A[last];
					maxSum = Math.max(sum, maxSum);
				}
			}
		}

		return maxSum;
	}

	public static void main(String[] args) {
		MaxSumOfRange task1 = new MaxSumOfRange();
		int result = task1.solution(new int[] { 5, 1, 4, 3 });
		System.out.println(result);
	}
}
