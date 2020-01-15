package com.universe.algorithm.interview;

/**
 * 接雨水
 * @author 刘亚楼
 * @date 2020/1/15
 */
public class RecieveWaterSolution {

	public static int solutionOfCalculatingSingleColumn(int[] array) {
		int length = array.length;
		int waterCount = 0;

		for (int col = 1; col < length - 1; col++) {
			int maxLeft = 0;
			int maxRight = 0;
			// 找出左边最高的
			for (int left = 0; left < col; left++) {
				maxLeft = Math.max(maxLeft, array[left]);
			}

			// 找出右边最高的
			for (int right = length - 1; right > col; right--) {
				maxRight = Math.max(maxRight, array[right]);
			}

			int min = Math.min(maxLeft, maxRight);
			if (min > array[col]) {
				waterCount += min - array[col];
			}
		}

		return waterCount;
	}

	/**
	 * 动态规划解决
	 * @return
	 */
	public static int solutionOfDynamicPlanning(int[] array) {
		int length = array.length;
		int[] maxLeft = new int[length];
		int[] maxRight = new int[length];
		for (int left = 1; left < length - 1; left++) {
			maxLeft[left] = Math.max(maxLeft[left - 1], array[left - 1]);
		}

		for (int right = length - 2; right > 0; right--) {
			maxRight[right] = Math.max(maxRight[right + 1], array[right + 1]);
		}

		int waterCount = 0;
		for (int col = 1; col < length - 1; col++) {
			int min = Math.min(maxLeft[col], maxRight[col]);
			if (min > array[col]) {
				waterCount += min - array[col];
			}
		}

		return waterCount;
	}

	/**
	 * 双指针减小空间复杂度
	 * @return
	 */
	public static int solutionOfDoublePointer(int[] array) {
		int sum = 0;
		int max_left = 0;
		int max_right = 0;
		int left = 1;
		int right = array.length - 2; // 加右指针进去
		for (int i = 1; i < array.length - 1; i++) {
			//从左到右遍历
			if (array[left - 1] < array[right + 1]) {
				max_left = Math.max(max_left, array[left - 1]);
				int min = max_left;
				if (min > array[left]) {
					sum = sum + (min - array[left]);
				}
				left++;
				//从右到左遍历
			} else {
				max_right = Math.max(max_right, array[right + 1]);
				int min = max_right;
				if (min > array[right]) {
					sum = sum + (min - array[right]);
				}
				right--;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		int[] array = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		int waterCount = solutionOfDoublePointer(array);
		System.out.println(waterCount);
	}

}
