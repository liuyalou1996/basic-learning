package com.universe.jdkapi.jdk8.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * @author: liuyalou
 * @since: 2019年5月14日
 * <p>
 * @Description:
 * DateTimeFormatter是线程安全的，可以替代Calendar
 */
public class DateTimeFormatterExample {

  public static void main(String[] args) {
    System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_TIME));
    System.out.println(LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
    System.out.println(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    System.out.println(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    // 自定义转换格式
    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String dateStr = formatter.format(LocalDateTime.now());
    System.out.println(dateStr);

    LocalDateTime dateTime = formatter.parse("2018-09-30 12:12:12", LocalDateTime::from);
    System.out.println(dateTime);
  }

}
