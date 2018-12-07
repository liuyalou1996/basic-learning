package com.iboxpay.jdk8.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class DateTimeApiTest {

  public static void testLocalDateTime() {
    LocalDateTime currentTime = LocalDateTime.now();
    System.out.println("当前日期时间为：" + currentTime);
    System.out.println("当前日期为：" + currentTime.toLocalDate());
    System.out.println("当前时间为：" + currentTime.toLocalTime());

    int month = currentTime.getMonthValue();
    int dayOfMonth = currentTime.getDayOfMonth();
    int hour = currentTime.getHour();
    int minute = currentTime.getMinute();
    int second = currentTime.getSecond();
    System.out.println("当前月：" + month + "，日：" + dayOfMonth + "，时：" + hour + "，分：" + minute + "，秒：" + second);

    System.out.println(LocalDateTime.of(2018, 12, 7, 17, 19, 50));
    System.out.println(LocalDate.of(2014, Month.DECEMBER, 7));
    System.out.println(LocalTime.of(15, 35));
    System.out.println(LocalTime.parse("17:22:22"));
  }

  public static void main(String[] args) {
    testLocalDateTime();
  }
}
