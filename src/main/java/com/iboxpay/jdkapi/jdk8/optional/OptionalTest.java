package com.iboxpay.jdkapi.jdk8.optional;

import java.util.Optional;

public class OptionalTest {

  public static void main(String[] args) {
    // 允许传入空参数
    Optional<Integer> a = Optional.ofNullable(null);
    Optional<Integer> b = Optional.of(new Integer(10));
    System.out.println("第一个参数值是否存在：" + a.isPresent());
    System.out.println("第二个参数值是否存在：" + b.isPresent());
    Integer value1 = a.orElse(Integer.valueOf(0));
    Integer value2 = b.get();
    System.out.println(value1 + " " + value2);
  }
}
