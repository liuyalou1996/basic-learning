package com.universe.algorithm.colidity;

/**
 * 盛水用到的最小水箱数量,H代表房子，-代表空地
 *
 * 示例1
 * 输入：-H-H-H--
 * 输出：2
 *
 * 示例2
 * 输入：HH-HH
 * 输出：-1
 *
 * 示例3
 * 输入：-H-H-H-H-H
 * 输出：3
 * @author 刘亚楼
 * @date 2022/6/6
 */
public class MinTanksOfReceivingWater {

	public int solution(String S) {
		int tanks = 0, length = S.length();
		for (int index = 0; index < length; index++) {
			if (S.charAt(index) == 'H') {
				if (index + 1 < length && S.charAt(index + 1) == '-') {
					tanks++;
					// 往右边放可直接跳过两个位置
					index += 2;
				} else if (index - 1 >= 0 && S.charAt(index - 1) == '-') {
					tanks++;
				} else {
					return -1;
				}
			}
		}
		return tanks;
	}

	public static void main(String[] args) {
		MinTanksOfReceivingWater task2 = new MinTanksOfReceivingWater();
		System.out.println(task2.solution("-H-H-H-H-H"));
	}
}
