package com.universe.thirdparty.emoji;

import com.vdurmont.emoji.EmojiParser;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Nick Liu
 * @date 2022/11/8
 */
public class EmojiProcessTest {

	public static void main(String[] args) throws Exception {
		String emojiStr = "An 😀awesome 😃string with a few 😉emojis!";

		String encodedEmojiStr = URLEncoder.encode(emojiStr, StandardCharsets.UTF_8.name());
		System.out.println("URL Encode后的表情包字符串：" + encodedEmojiStr);
		String decodedEmojiStr = URLDecoder.decode(encodedEmojiStr, StandardCharsets.UTF_8.name());
		System.out.println("URL Decide后的表情包字符串：" + decodedEmojiStr);

		String emojoAliases = EmojiParser.parseToAliases(emojiStr);
		System.out.println("表情包转别名：" + emojoAliases);

		String emojiUnicodeStr = EmojiParser.parseToUnicode(emojoAliases);
		System.out.println("别名转Unicode：" + emojiUnicodeStr);
	}
}
