package com.iboxpay.jdk8.streamOp;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StreamOpTest {

  public static void main(String[] args) {
    Random random = new Random();
    random.ints().limit(10).sorted().forEach(System.out::println);

    List<String> nameList = Arrays.asList("lyl", "", "lq", "zxq", "wm");
    nameList.stream().filter(name -> !name.isEmpty()).sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList());
    System.out.println(nameList);
  }
}
