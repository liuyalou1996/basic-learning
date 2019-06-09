package com.universe.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 将Java堆大小设为20m，官方推荐初始堆和最大堆大小保持一致，HeapDumpOnOutOfMemoryError参数用来导出错误信息
 */
public class HeapOOMExample {

  static class OOMObject {

  }

  public static void main(String[] args) {
    List<OOMObject> list = new ArrayList<>();
    while (true) {
      list.add(new OOMObject());
    }
  }
}
