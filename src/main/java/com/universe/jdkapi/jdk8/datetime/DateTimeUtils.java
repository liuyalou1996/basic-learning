package com.universe.jdkapi.jdk8.datetime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘亚楼
 * @date 2020/3/13
 */
public class DateTimeUtils {

	private static final ZoneId SYSTEM_DEFAULT_ZONEID = ZoneId.systemDefault();

	private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

	public static String format(Date date, Pattern pattern) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return format(localDateTime, pattern);
	}

	public static LocalDate parseToLocalDate(String text, Pattern pattern) {
		return parse(text, pattern, LocalDate::from);
	}

	public static LocalDateTime parseToLocalDateTime(String text, Pattern pattern) {
		return parse(text, pattern, LocalDateTime::from);
	}

	/**
	 * 将时间或日期转换为字符串，转换模式必须与TemporalAccessor保持一致
	 * @param temporal Temporal实例，主要有LocalTime、LocalDate和LocalDateTime
	 * @param pattern 转换模式，和SimpleDateFormat一致
	 * @return 格式化日期字符串
	 */
	public static String format(Temporal temporal, Pattern pattern) {
		DateTimeFormatter formatter = getDateTimeFormatter(pattern.getFormat());
		return formatter.format(temporal);
	}

	/**
	 * 将时间或日期字符串转换为时间或日期，时间或日期字符串、转换格式、TemporalQuery必须保持一致
	 * @param text 时间或日期字符串
	 * @param pattern 转换模式，通过Pattern.of("yyyyMMdd")实例化，格式和SimpleDateFormat一致
	 * @param query 函数式接口，通过LocalTime、LocalDate、LocalDateTime的静态方法from()可实现，如LocalDate::from
	 * @return LocalTime、LocalDate、LocalDateTime实例
	 */
	public static <T> T parse(String text, Pattern pattern, TemporalQuery<T> query) {
		DateTimeFormatter formatter = getDateTimeFormatter(pattern.getFormat());
		return formatter.parse(text, query);
	}

	/**
	 * Date转LocalDateTime实例
	 * @param date
	 * @return LocalDateTime实例
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		return instant.atZone(SYSTEM_DEFAULT_ZONEID).toLocalDateTime();
	}

	/**
	 * Date转LocalDate实例
	 * @param date
	 * @return
	 */
	public static LocalDate dateToLocaLocalDate(Date date) {
		return dateToLocalDateTime(date).toLocalDate();
	}

	/**
	 * LocalDateTime实例转Date实例
	 * @param localDateTime
	 * @return Date实例
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(SYSTEM_DEFAULT_ZONEID).toInstant();
		return Date.from(instant);
	}

	/**
	 * LocalDate实例转Date实例
	 * @param localDate
	 * @return Date实例
	 */
	public static Date localDateToDate(LocalDate localDate) {
		Instant instant = localDate.atStartOfDay().atZone(SYSTEM_DEFAULT_ZONEID).toInstant();
		return Date.from(instant);
	}

	/**
	 * 毫秒级时间戳转LocaDateTime实例
	 * @param timestampInMillis
	 * @return LocalDateTime实例
	 */
	public static LocalDateTime timestampToLocalDateTime(long timestampInMillis) {
		Instant instant = Instant.ofEpochMilli(timestampInMillis);
		return LocalDateTime.ofInstant(instant, SYSTEM_DEFAULT_ZONEID);
	}

	/**
	 * LocalDateTime实例转毫秒时间戳
	 * @param localDateTime
	 * @return 毫秒级时间戳
	 */
	public static long localDateTimeToTimestamp(LocalDateTime localDateTime) {
		return localDateTime.atZone(SYSTEM_DEFAULT_ZONEID).toInstant().toEpochMilli();
	}

	/**
	 * Date实例转毫秒级时间戳
	 * @param date
	 * @return
	 */
	public static long dateToTimestamp(Date date) {
		return date.getTime();
	}

	/**
	 * 毫秒级时间戳转Date实例
	 * @param timestampInMillis
	 * @return
	 */
	public static Date timestampToDate(long timestampInMillis) {
		return new Date(timestampInMillis);
	}

	/**
	 * Unix秒级时间戳
	 * @return
	 */
	public static long getUnixTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 计算月份差，Temporal实例有LocalDate、LocalDateTime等
	 * @param former
	 * @param latter
	 * @return
	 */
	public static long monthsBetween(Temporal former, Temporal latter) {
		return ChronoUnit.MONTHS.between(former, latter);
	}

	/**
	 * 计算日期差，Temporal实例有LocalDate、LocalDateTime等
	 * @param former
	 * @param latter
	 * @return
	 */
	public static long daysBetween(Temporal former, Temporal latter) {
		return ChronoUnit.DAYS.between(former, latter);
	}

	/**
	 * 计算时间差，Temporal实例有LocalDate、LocalDateTime等
	 * @param former
	 * @param latter
	 * @return
	 */
	public static long hoursBetween(Temporal former, Temporal latter) {
		return ChronoUnit.HOURS.between(former, latter);
	}

	private static DateTimeFormatter getDateTimeFormatter(String pattern) {
		DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
		if (formatter == null) {
			formatter = DateTimeFormatter.ofPattern(pattern);
			FORMATTER_CACHE.put(pattern, formatter);
		}

		return formatter;
	}

	public static class Pattern {

		public static final Pattern YEAR_TO_DAY = Pattern.of("yyyyMMdd");
		public static final Pattern YEAR_TO_DAY_WITH_STRIKE = Pattern.of("yyyy-MM-dd");
		public static final Pattern YEAR_TO_DAY_WITH_SLASH = Pattern.of("yyyy/MM/dd");
		public static final Pattern YEAR_TO_SECOND_WITH_STRIKE = Pattern.of("yyyy-MM-dd HH:mm:ss");
		public static final Pattern YEAR_TO_SECOND = Pattern.of("yyyyMMddHHmmss");
		public static final Pattern YEAR_TO_MILLIS_WITH_STRIKE = Pattern.of("yyyy-MM-dd HH:mm:ss.SSS");
		public static final Pattern YEAR_TO_MILLIS = Pattern.of("yyyyMMddHHmmssSSS");
		public static final Pattern MONTH_TO_SECOND = Pattern.of("MMddHHmmss");

		private String format;

		private Pattern(String format) {
			this.format = format;
		}

		public String getFormat() {
			return format;
		}

		public static Pattern of(String pattern) {
			return new Pattern(pattern);
		}

		@Override
		public String toString() {
			return "Pattern [format=" + format + "]";
		}
	}
}
