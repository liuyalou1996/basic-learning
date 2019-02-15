package com.iboxpay.jdkapi.jdk8.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {

  public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final String DATE_TIME_MILLS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

  private static DateFormat df = new SimpleDateFormat(DEFAULT_FORMAT);

  public static Date parseStringToDate(String dateStr) throws ParseException {
    return parseStringToDate(dateStr, null);
  }

  public static Date parseStringToDate(String dateStr, String format) throws ParseException {
    if (StringUtils.isNotEmpty(format)) {
      df = new SimpleDateFormat(format);
    }
    return df.parse(dateStr);
  }

  public static String parseDateToString(Date date) {
    return parseDateToString(date, null);
  }

  public static String parseDateToString(Date date, String format) {
    if (StringUtils.isNotEmpty(format)) {
      df = new SimpleDateFormat(format);
    }
    return df.format(date);
  }

}
