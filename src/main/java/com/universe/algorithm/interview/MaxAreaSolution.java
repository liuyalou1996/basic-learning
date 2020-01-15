package com.universe.algorithm.interview;

/**
 * 两个坐标之间求最大面积
 * @author 刘亚楼
 * @date 2020/1/15
 */
public class MaxAreaSolution {

	/**
	 * 暴力破解
	 * @param array
	 * @return
	 */
	public static int solutionOfViolentCrack(int[] array) {
		int arrayLength = array.length;
		int height = 0;
		int capacity = 0;
		int maxCapacity = 0;

		for (int i = 0; i < arrayLength - 1; i++) {
			for (int j = arrayLength - 1; j > i; j--) {
				height = Math.min(array[i], array[j]);
				capacity = (j - i) * height;
				if (maxCapacity < capacity) {
					maxCapacity = capacity;
				}
			}
		}

		return maxCapacity;
	}

	/**
	 * 双指针减小时间复杂度
	 * @param array
	 * @return
	 */
	public static int solutionOfDoublePointer(int[] array) {
		int arrayLength = array.length;
		int capacity = 0;
		int maxCapacity = 0;

		int leftPointer = 0;
		int rightPointer = arrayLength - 1;
		while (leftPointer < rightPointer) {
			capacity = Math.min(array[leftPointer], array[rightPointer]) * (rightPointer - leftPointer);
			maxCapacity = Math.max(maxCapacity, capacity);
			if (array[leftPointer] < array[rightPointer]) {
				leftPointer++;
			} else {
				rightPointer--;
			}
		}

		return maxCapacity;
	}

	public static void main(String[] args) {
		System.out.println(solutionOfDoublePointer(new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 }));
	}
}
