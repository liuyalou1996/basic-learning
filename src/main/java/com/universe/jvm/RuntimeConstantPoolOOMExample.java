package com.universe.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args:-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 * 这段代码在jdk1.6中运行会出现常量池oom，因为常量池分配在永久代内，1.6以上版本不会
 */
public class RuntimeConstantPoolOOMExample {

  public static void main(String[] args) {
    // 通过list保持常量池中字符串的引用，避免Full GC回收常量池行为
    List<String> list = new ArrayList<>();
    int i = 0;
    while (true) {
      list.add(String.valueOf(i++).intern());
    }
  }
}
