package com.universe.algorithm.leetcode.middle;

/**
 * https://leetcode.cn/problems/reverse-integer/
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围[−231, 231− 1] ，就返回 0。
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 *
 * 输入：x = 123
 * 输出：321
 *
 * 输入：x = -123
 * 输出：-321
 *
 * 输入：x = 120
 * 输出：21
 * https://leetcode.cn/problems/reverse-integer/solution/zheng-shu-fan-zhuan-by-leetcode-solution-bccn/
 * @author 刘亚楼
 * @date 2022/6/4
 */
public class ReverseInteger {

	public int reverse(int num) {
		int reverse = 0, mod = 0;

		while (num != 0) {
			if (reverse > Integer.MAX_VALUE / 10 || reverse < Integer.MIN_VALUE / 10) {
				return 0;
			}

			mod = num % 10;
			reverse = reverse * 10 + mod;
			num /= 10;
		}

		return reverse;
	}

	public static void main(String[] args) {
		ReverseInteger reverseInteger = new ReverseInteger();
		System.out.println(reverseInteger.reverse(-123));
	}
}
