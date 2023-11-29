package com.universe.thirdparty.nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;

import java.util.List;

/**
 * <p>HanLP是一系列模型与算法组成的NLP工具包，目标是普及自然语言处理在生产环境中的应用。</p> <br/>
 * <p>HanLP具备功能完善、性能高效、架构清晰、语料时新、可自定义的特点，可以用作中文分词、关键词提取、短语提取、拼音转换、简繁转换等等。</p><br/>
 * <p>更多信息参考：<a href="https://github.com/hankcs/HanLP/tree/1.x">HanLP github</a></p>
 * @author Nick Liu
 * @date 2023/11/29
 */
public class HanLpExample {

	public static void main(String[] args) {
		String text = "知之为知之不知为不知是智也";
		List<Pinyin> pinyinList = HanLP.convertToPinyinList(text);
		for (int index = 0; index < pinyinList.size(); index++) {
			Pinyin pinyin = pinyinList.get(index);
			StringBuilder sb = new StringBuilder(String.format("汉字: %s", text.charAt(index))).append(",")
				.append(String.format("无声调拼音: %s", pinyin.getPinyinWithoutTone())).append(",")
				.append(String.format("带声调拼音: %s", pinyin.getPinyinWithToneMark())).append(",")
				.append(String.format("声母: %s", pinyin.getShengmu())).append(",")
				.append(String.format("韵母: %s", pinyin.getYunmu())).append(",")
				.append(String.format("声调: %s", pinyin.getTone())).append(",");
			System.out.println(sb);
		}
	}
}
