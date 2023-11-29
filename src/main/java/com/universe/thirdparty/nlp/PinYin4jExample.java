package com.universe.thirdparty.nlp;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Arrays;

/**
 * 汉语转拼音处理，使用简单，功能较为基础。该工具包最晚法行为2016年，已经比较老。<br/>
 * 详情查看：<a href="https://sourceforge.net/projects/pinyin4j/">pinyin4j官网</a> <br/>
 * github参考：<a href="https://github.com/belerweb/pinyin4j">pinyin4j github</a> <br/>
 *
 * @author Nick Liu
 * @date 2023/11/29
 */
public class PinYin4jExample {

	public static void main(String[] args) {
		String text = "你好世界";
		char[] array = text.toCharArray();
		for (int index = 0; index < array.length; index++) {
			String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(array[index]);
			System.out.println(String.format("汉字:%s, 对应的拼音: %s", array[index], Arrays.toString(pinyin)));
		}
	}
}
