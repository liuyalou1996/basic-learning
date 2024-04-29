package com.universe.thirdparty.nlp;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * @author Nick Liu
 * @date 2024/4/29
 */
public class JpinYinExample {

	public static void main(String[] args) throws PinyinException {
		String pinyin = PinyinHelper.convertToPinyinString("吕强", " ");
		System.out.println(pinyin);
	}
}
