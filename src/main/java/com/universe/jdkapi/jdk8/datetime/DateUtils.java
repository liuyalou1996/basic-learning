package com.universe.jdkapi.jdk8.datetime;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class DateUtils {

  private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

  public static String format(TemporalAccessor temporal, Pattern pattern) {
    DateTimeFormatter formatter = getDateTimeFormatter(pattern.getFormat());
    return formatter.format(temporal);
  }

  /**
   * 将时间或日期转换为字符串，转换模式必须与TemporalAccessor保持一致
   * @param temporal TemporalAccessor实例，主要有LocalTime、LocalDate和LocalDateTime
   * @param pattern 转换模式，和SimpleDateFormat一致
   * @return
   */
  public static String format(TemporalAccessor temporal, String pattern) {
    DateTimeFormatter formatter = getDateTimeFormatter(pattern);
    return formatter.format(temporal);
  }

  public static <T> T parse(String text, Pattern pattern, TemporalQuery<T> query) {
    DateTimeFormatter formatter = getDateTimeFormatter(pattern.getFormat());
    return formatter.parse(text, query);
  }

  /**
   * 将时间或日期字符串转换为时间或日期，时间或日期字符串、转换格式、TemporalQuery必须保持一致
   * @param text 时间或日期字符串
   * @param pattern 转换模式，和SimpleDateFormat一致
   * @param query 函数式接口，通过LocalTime、LocalDate、LocalDateTime的静态方法from()可实现，如LocalDate::from
   * @return
   */
  public static <T> T parse(String text, String pattern, TemporalQuery<T> query) {
    DateTimeFormatter formatter = getDateTimeFormatter(pattern);
    return formatter.parse(text, query);
  }

  private static DateTimeFormatter getDateTimeFormatter(String pattern) {
    DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
    if (formatter == null) {
      formatter = DateTimeFormatter.ofPattern(pattern);
      FORMATTER_CACHE.put(pattern, formatter);
    }

    return formatter;
  }

  public static enum Pattern {

    DATE_WITHOUT_STRIKE("yyyyMMdd"),

    DATE_WITH_STRIKE("yyyy-MM-dd"),

    DATE_WITH_SLASH("yyyy/MM/dd"),

    DATE_TIME_WITH_STRIKE("yyyy-MM-dd HH:mm:ss"),

    DATE_TIME_WITHOUT_STRIKE("yyyyMMddHHmmss"),

    DATE_TIME_MILLS_WITH_STRIKE("yyyy-MM-dd HH:mm:ss.SSS"),

    DATE_TIME_MILLS_WITHOUT_STRIKE("yyyyMMddHHmmssSSS");

    private String format;

    private Pattern(String format) {
      this.format = format;
    }

    public String getFormat() {
      return format;
    }
  }

}
