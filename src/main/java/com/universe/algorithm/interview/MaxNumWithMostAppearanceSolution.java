package com.universe.algorithm.interview;

/**
 * 给定一个数组，输出最大且出现次数最多的数字
 * @author 刘亚楼
 * @date 2020/10/12
 */
public class MaxNumWithMostAppearanceSolution {

	public static void main(String[] args) {
		int[] arr = new int[] { 1, 3, 4, 7, 7, 1, 8, 1, 2, 8, 8, 8 };

		int maxAppearance = 0;
		int maxNum = 0;
		int numWithMaxAppearance = 0;

		int appearanceOfNum;
		for (int index = 0; index < arr.length; index++) {
			appearanceOfNum = 1;

			for (int slot = index + 1; slot < arr.length; slot++) {
				if (arr[slot] == arr[index]) {
					appearanceOfNum++;
				}
			}

			if (arr[index] > maxNum) {
				maxNum = arr[index];
			}

			if (appearanceOfNum > maxAppearance) {
				maxAppearance = appearanceOfNum;
				numWithMaxAppearance = arr[index];
			}
		}

		if (maxNum == numWithMaxAppearance) {
			System.out.println("最大且出现最多的数为：" + maxNum + "，出现次数为：" + maxAppearance);
			return;
		}

		System.out.println("查无次数");
	}

}
