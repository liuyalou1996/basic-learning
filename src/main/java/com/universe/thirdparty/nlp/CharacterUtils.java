package com.universe.thirdparty.nlp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘亚楼
 * @date 2022/9/15
 */
@Slf4j
public class CharacterUtils {

	/**
	 * 带中文空格的正则, 如匹配: "刘 亚楼"
	 */
	public static final Pattern CHINESE_PATTERN_WITH_SPACE = Pattern.compile("[\\u4e00-\\u9fa5]+(\\s+)[\\u4e00-\\u9fa5]+");
	/**
	 * 中文正则表达式，除了中文汉字还有标点符号：。 ？ ！ ， 、 ； ： “ ” ‘ ' （ ） 《 》 〈 〉 【 】 『 』 「 」 ﹃ ﹄ 〔 〕 … — ～ ﹏ ￥
	 */
	public static final Pattern CHINESE_PATTERN = Pattern.compile("([\\u4e00-\\u9fa5]|[\\u3002|\\uff1f|\\uff01|\\uff0c|\\u3001|\\uff1b|\\uff1a|\\u201c|\\u201d|\\u2018|\\u2019|\\uff08|\\uff09|\\u300a|\\u300b|\\u3008|\\u3009|\\u3010|\\u3011|\\u300e|\\u300f|\\u300c|\\u300d|\\ufe43|\\ufe44|\\u3014|\\u3015|\\u2026|\\u2014|\\uff5e|\\ufe4f|\\uffe5])");

	/**
	 * 中文空格正则表达式
	 */
	public static final Pattern CHINESE_SPACE_PATTERN = Pattern.compile("([\\u4e00-\\u9fa5\\s]|[\\u3002|\\uff1f|\\uff01|\\uff0c|\\u3001|\\uff1b|\\uff1a|\\u201c|\\u201d|\\u2018|\\u2019|\\uff08|\\uff09|\\u300a|\\u300b|\\u3008|\\u3009|\\u3010|\\u3011|\\u300e|\\u300f|\\u300c|\\u300d|\\ufe43|\\ufe44|\\u3014|\\u3015|\\u2026|\\u2014|\\uff5e|\\ufe4f|\\uffe5])+");

	/**
	 * 字母、数字、中文正则表达式(ANC)
	 */
	public static final Pattern ALPHA_NUMERIC_CHINESE_PATTERN = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5]+");
	/**
	 * 字母(A)
	 */
	public static final Pattern ALPHABETIC_PATTERN = Pattern.compile("[a-zA-Z]");
	/**
	 * 数字
	 */
	public static final Pattern NUMERIC_PATTERN = Pattern.compile("[0-9]");

	/**
	 * 多个空格
	 */
	public static final Pattern SPACE_PATTERN = Pattern.compile("\\s");

	/**
	 * 字母，数字正则表达式(AN)
	 */
	public static final Pattern ALPHA_NUMERIC_PATTERN = Pattern.compile("[0-9a-zA-Z]");

	/**
	 * 字母，数字，空格正则表达式(AN)
	 */
	public static final Pattern ALPHA_NUMERIC_SPACE_PATTERN = Pattern.compile("[0-9a-zA-Z\\s]+");
	/**
	 * 字母、数字、中划线正则表达式(AND)
	 */
	public static final Pattern ALPHA_NUMERIC_WITH_DASH_PATTERN = Pattern.compile("([0-9a-zA-Z]|-)+");
	/**
	 * 字符串(G_13)正则表达式
	 */
	public static final Pattern STRING_PATTERN = Pattern.compile("([0-9a-zA-Z]|\\s|[!#$%'()*+,\\-./:;=?@\\[\\]^_`{}~])+");

	/**
	 * 特殊字符正则表达式
	 */
	public static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("[!#$%'()*+,\\-./:;=?@\\[\\]^_`{}~]");

	/**
	 * 全字母、数字、空格正则表达式
	 */
	public static final Pattern ALL_AN_SPACE_PATTERN = Pattern.compile("^[0-9a-zA-Z\\s]+$");

	/**
	 * 全中文、空格正则表达式
	 */
	public static final Pattern ALL_CHINESE_SPACE_PATTERN = Pattern.compile("^[\\u4e00-\\u9fa5\\s]+$");

	/**
	 * 是否是中文
	 * @param text
	 * @return
	 */
	public static boolean isChineseCharacter(String text) {
		return CHINESE_SPACE_PATTERN.matcher(text).matches();
	}

	/**
	 * 替代中文字符之间的空格
	 *
	 * @param chineseCharacter
	 * @return
	 */
	public static String replaceSpaceIfChineseCharacter(String chineseCharacter) {
		String newChineseCharacter = chineseCharacter;

		Matcher matcher = CHINESE_PATTERN_WITH_SPACE.matcher(chineseCharacter);
		if (matcher.find()) {
			newChineseCharacter = chineseCharacter.replace(matcher.group(1), StringUtils.EMPTY);
		}
		return newChineseCharacter;
	}

	/**
	 * 统计各种字符数量
	 * @param str
	 * @return
	 */
	public static CharacterStatistics characterStatistics(String str) {
		if (StringUtils.isBlank(str)) {
			return new CharacterStatistics();
		}

		CharacterStatistics characterStatistics = new CharacterStatistics();
		Matcher alpabeticNumericMatcher = ALPHABETIC_PATTERN.matcher(str);
		while (alpabeticNumericMatcher.find()) {
			characterStatistics.setAlphabetCharacterCount(characterStatistics.getAlphabetCharacterCount() + 1);
		}
		Matcher numericMatcher = NUMERIC_PATTERN.matcher(str);
		while (numericMatcher.find()) {
			characterStatistics.setNumericCharacterCount(characterStatistics.getNumericCharacterCount() + 1);
		}
		Matcher chineseMatcher = CHINESE_PATTERN.matcher(str);
		while (chineseMatcher.find()) {
			characterStatistics.setChineseCharacterCount(characterStatistics.getChineseCharacterCount() + 1);
		}
		Matcher specialMatcher = SPECIAL_CHARACTER_PATTERN.matcher(str);
		while (specialMatcher.find()) {
			characterStatistics.setSpecialCharacterCount(characterStatistics.getSpecialCharacterCount() + 1);
		}
		Matcher spaceMatcher = SPACE_PATTERN.matcher(str);
		while (spaceMatcher.find()) {
			characterStatistics.setSpaceCharacterCount(characterStatistics.getSpaceCharacterCount() + 1);
		}
		characterStatistics.setAllChineseOrSpace(CHINESE_SPACE_PATTERN.matcher(str).matches());
		characterStatistics.setAllAlphaNumericOrSpace(ALPHA_NUMERIC_SPACE_PATTERN.matcher(str).matches());
		return characterStatistics;
	}

	public static void main(String[] args) {

	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CharacterStatistics {

		private int alphabetCharacterCount;
		private int numericCharacterCount;
		private int chineseCharacterCount;
		private int spaceCharacterCount;

		/**
		 * 特殊字符数
		 */
		private int specialCharacterCount;
		/**
		 * 是否全为中文或者空格
		 */
		private boolean isAllChineseOrSpace;
		/**
		 * 是否全为字母、数字或者空格
		 */
		private boolean isAllAlphaNumericOrSpace;

		public boolean containChineseCharacter() {
			return chineseCharacterCount > 0;
		}

	}

}
