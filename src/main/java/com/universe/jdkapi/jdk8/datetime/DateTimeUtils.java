package com.universe.jdkapi.jdk8.datetime;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DateTimeUtils {

	private static final ZoneId SYSTEM_DEFAULT_ZONEID = ZoneId.systemDefault();

	private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

	public static String format(Date date, Pattern pattern) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return format(localDateTime, pattern);
	}

	public static String format(TemporalAccessor temporal, Pattern pattern) {
		DateTimeFormatter formatter = getDateTimeFormatter(pattern.getFormat());
		return formatter.format(temporal);
	}

	public static String format(Date date, String pattern) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return format(localDateTime, pattern);
	}

	/**
	 * 将时间或日期转换为字符串，转换模式必须与TemporalAccessor保持一致
	 * @param temporal TemporalAccessor实例，主要有LocalTime、LocalDate和LocalDateTime
	 * @param pattern 转换模式，和SimpleDateFormat一致
	 * @return 格式化日期字符串
	 */
	public static String format(TemporalAccessor temporal, String pattern) {
		DateTimeFormatter formatter = getDateTimeFormatter(pattern);
		return formatter.format(temporal);
	}

	public static Date parse(String text, Pattern pattern) {
		LocalDateTime localDateTime = parse(text, pattern, LocalDateTime::from);
		return localDateTimeToDate(localDateTime);
	}

	public static <T> T parse(String text, Pattern pattern, TemporalQuery<T> query) {
		DateTimeFormatter formatter = getDateTimeFormatter(pattern.getFormat());
		return formatter.parse(text, query);
	}

	public static Date parse(String text, String pattern) {
		LocalDateTime localDateTime = parse(text, pattern, LocalDateTime::from);
		return localDateTimeToDate(localDateTime);
	}

	/**
	 * 将时间或日期字符串转换为时间或日期，时间或日期字符串、转换格式、TemporalQuery必须保持一致
	 * @param text 时间或日期字符串
	 * @param pattern 转换模式，和SimpleDateFormat一致
	 * @param query 函数式接口，通过LocalTime、LocalDate、LocalDateTime的静态方法from()可实现，如LocalDate::from
	 * @return LocalTime、LocalDate、LocalDateTime实例
	 */
	public static <T> T parse(String text, String pattern, TemporalQuery<T> query) {
		DateTimeFormatter formatter = getDateTimeFormatter(pattern);
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
	 * LocalDateTime实例转Date实例
	 * @param localDateTime
	 * @return Date实例
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(SYSTEM_DEFAULT_ZONEID).toInstant();
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

	public static boolean isBefore(LocalDateTime former, LocalDateTime latter) {
		return former.isBefore(latter);
	}

	public static boolean isAfter(LocalDateTime former, LocalDateTime latter) {
		return former.isAfter(latter);
	}

	public static boolean isEqual(LocalDateTime former, LocalDateTime latter) {
		return former.isEqual(latter);
	}

	public static int monthsBetween(LocalDateTime former, LocalDateTime latter) {
		return (latter.getYear() - former.getYear()) * 12 + (latter.getMonthValue() - former.getMonthValue());
	}

	public static long daysBetween(LocalDateTime former, LocalDateTime latter) {
		// ChronoUnit.DAYS.between(former, latter);
		return Duration.between(former, latter).toDays();
	}

	public static long hoursBetween(LocalDateTime former, LocalDateTime latter) {
		// ChronoUnit.HOURS.between(former, latter);
		return Duration.between(former, latter).toHours();
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

		public static final Pattern DATE_WITHOUT_STRIKE = Pattern.of("yyyyMMdd");
		public static final Pattern DATE_WITH_STRIKE = Pattern.of("yyyy-MM-dd");
		public static final Pattern DATE_WITH_SLASH = Pattern.of("yyyy/MM/dd");
		public static final Pattern DATE_TIME_WITH_STRIKE = Pattern.of("yyyy-MM-dd HH:mm:ss");
		public static final Pattern DATE_TIME_WITHOUT_STRIKE = Pattern.of("yyyyMMddHHmmss");
		public static final Pattern DATE_TIME_MILLS_WITH_STRIKE = Pattern.of("yyyy-MM-dd HH:mm:ss.SSS");
		public static final Pattern DATE_TIME_MILLS_WITHOUT_STRIKE = Pattern.of("yyyyMMddHHmmssSSS");

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
