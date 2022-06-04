package com.universe.algorithm.leetcode.middle;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/longest-substring-without-repeating-characters/
 *
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * https://leetcode.cn/problems/longest-substring-without-repeating-characters/solution/hua-jie-suan-fa-3-wu-zhong-fu-zi-fu-de-zui-chang-z/
 * @author 刘亚楼
 * @date 2022/6/3
 */
public class LongestUnrepeatableStr {

	public int lengthOfLongestSubstring(String str) {
		// 用map存储每个元素对应的位置下标
		Map<Character, Integer> map = new HashMap<>();

		int maxLengthOfStr = 0, length = str.length();
		for (int start = 0, end = 0; end < length; end++) {
			char alphabet = str.charAt(end);
			if (map.containsKey(alphabet)) {
				// 需要改变start的值，这里start的值有两种情况，如dvdf,abba
				start = Math.max(start, map.get(alphabet) + 1);
			}

			map.put(alphabet, end);
			maxLengthOfStr = Math.max(maxLengthOfStr, end - start + 1);
		}

		return maxLengthOfStr;
	}

	public static void main(String[] args) {
		LongestUnrepeatableStr test = new LongestUnrepeatableStr();
		int length = test.lengthOfLongestSubstring("abcabcbb");
		System.out.println(length);
	}
}
