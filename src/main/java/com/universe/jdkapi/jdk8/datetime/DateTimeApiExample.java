package com.universe.jdkapi.jdk8.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;

public class DateTimeApiExample {

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

		LocalDate currentDate = LocalDate.now();
		System.out.println("当天是一周的第几天：" + currentDate.get(ChronoField.DAY_OF_WEEK));
		System.out.println("当天是一月的第几天：" + currentDate.get(ChronoField.DAY_OF_MONTH));
		System.out.println("当天是一年的第几天：" + currentDate.get(ChronoField.DAY_OF_YEAR));
		System.out.println("当周是一月的第几周：" + currentDate.get(ChronoField.ALIGNED_WEEK_OF_MONTH));
		System.out.println("当周是一年的第几周：" + currentDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
		System.out.println("当月是一年的第几月：" + currentDate.get(ChronoField.MONTH_OF_YEAR));

		System.out.println("日期转换为一周的星期五：" + currentDate.with(ChronoField.DAY_OF_WEEK, DayOfWeek.FRIDAY.getValue()));
		System.out.println("日期转化为一月的最后一天" + currentDate.with(TemporalAdjusters.lastDayOfMonth()));
		System.out.println("日期转化为一年的最后一天" + currentDate.with(TemporalAdjusters.lastDayOfYear()));
	}

	public static void main(String[] args) {
		testLocalDateTime();
	}
}
