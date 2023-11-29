package com.universe.thirdparty.nlp;

import com.hankcs.hanlp.HanLP;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>中文字符处理工具类，该工具类主要借助HanLP。</p> <br/>
 * <p>HanLP是一系列模型与算法组成的NLP工具包，目标是普及自然语言处理在生产环境中的应用。</p> <br/>
 * <p>HanLP具备功能完善、性能高效、架构清晰、语料时新、可自定义的特点，可以用作中文分词、关键词提取、短语提取、拼音转换、简繁转换等等。</p><br/>
 * <p>更多信息参考：<a href="https://github.com/hankcs/HanLP/tree/1.x">HanLP github</a></p>
 *
 * @author Nick Liu
 * @date 2023/11/29
 */
public abstract class ChineseCharacterUtils {

	/**
	 * 中文字符转为拼音
	 *
	 * @param text 中文字符
	 * @return 中文字符则会转为对应的拼音，英文字符则原字符输出
	 */
	public static String toPinYin(String text) {
		return HanLP.convertToPinyinString(text, StringUtils.EMPTY, false);
	}

	/**
	 * 中文字符转为大写拼音
	 *
	 * @param text 中文字符
	 * @return 中文字符则会转为对应的大写拼音，英文字符则原字符大写输出
	 */
	public static String toUpperCasePinYin(String text) {
		String pinyin = HanLP.convertToPinyinString(text, StringUtils.EMPTY, false);
		return StringUtils.upperCase(pinyin);
	}

	/**
	 * 中文字符转为拼音，第一个拼音与后面的拼音分隔符分隔
	 *
	 * @param text      中文字符
	 * @param separator 分隔符
	 * @return 中文字符则会转为对应的拼音，并且首个拼音与后面的拼音分隔符分隔，英文字符则原字符输出
	 */
	public static String toPinYinWithFirstSeparated(String text, String separator) {
		if (!CharacterUtils.isChineseCharacter(text)) {
			return text;
		}

		String separatedChinesText = StringUtils.overlay(text, separator, 1, 1);
		return toPinYin(separatedChinesText);
	}

	/**
	 * 中文字符转为大写拼音，第一个拼音与后面的拼音分隔符分隔
	 *
	 * @param text      中文字符
	 * @param separator 分隔符
	 * @return 中文字符则会转为对应的拼音，并且首个拼音与后面的拼音分隔符分隔，，英文字符则原字符大写输出
	 */
	public static String toUpperCasePinYinWithFirstSeparated(String text, String separator) {
		return StringUtils.upperCase(toPinYinWithFirstSeparated(text, separator));
	}

	/**
	 * 简体中文转繁体中文
	 *
	 * @param traditionalChineseText 繁体中文
	 * @return 如果是英文则返回原值，中文则转成简体中文
	 */
	public static String toSimplifiedChinese(String traditionalChineseText) {
		return HanLP.convertToSimplifiedChinese(traditionalChineseText);
	}

	/**
	 * 繁体中文转简体中文
	 *
	 * @param simplifiedChineseText 简体中文
	 * @return 如果英文则返回原值，中文则转成繁体中文
	 */
	public static String toTraditionalChinese(String simplifiedChineseText) {
		return HanLP.convertToTraditionalChinese(simplifiedChineseText);
	}

	public static void main(String[] args) {
		String chineseText = "刘亚楼";
		String traditionalChineseText = "刘亚楼";
		System.out.println("中文转拼音: " + toPinYin(chineseText));
		System.out.println("中文转拼音: " + toUpperCasePinYin(chineseText));
		System.out.println("中文转拼音(首个拼音分隔): " + toPinYinWithFirstSeparated(chineseText, StringUtils.SPACE));
		System.out.println("中文转大写拼音(首个拼音分隔): " + toUpperCasePinYinWithFirstSeparated(chineseText, StringUtils.SPACE));
		System.out.println("简体中文转繁体: " + toTraditionalChinese(chineseText));
		System.out.println("繁体中文转简体: " + toSimplifiedChinese(traditionalChineseText));
	}
}
