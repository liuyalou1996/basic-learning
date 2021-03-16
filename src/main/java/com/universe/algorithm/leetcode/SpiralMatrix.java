package com.universe.algorithm.leetcode;

import java.util.Arrays;

/**
 * 螺旋矩阵
 * @author 刘亚楼
 * @date 2021/3/16
 */
public class SpiralMatrix {

	public static void main(String[] args) {
		System.out.println(Arrays.toString(generateMatrix(5)));
	}

	public static int[][] generateMatrix(int n) {
		int[][] array = new int[n][n];

		int start = 1;

		int leftTopX = 0, leftTopY = 0;
		int rightTopX = n - 1, rightTopY = 0;
		int leftDownX = 0, leftDownY = n - 1;
		int rightDownX = n - 1, rightDownY = n - 1;

		while (start < n * n) {
			// 从左上角至右上角，纵坐标不变，横坐标变
			for (int temp = leftTopX; temp <= rightTopX; temp++) {
				array[leftTopY][temp] = start++;
			}

			// 从右上角到右下角，横坐标不变，纵坐标变
			for (int temp = rightTopY + 1; temp <= rightDownY; temp++) {
				array[temp][rightTopX] = start++;
			}

			// 从右下角到左下角，纵坐标不变，横坐标变
			for (int x = rightDownX - 1; x >= leftDownX; x--) {
				array[leftDownY][x] = start++;
			}
		}

		return array;
	}
}
