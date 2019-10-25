package com.universe.thirdparty.easyexcel;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import com.alibaba.excel.EasyExcel;
import com.universe.thirdparty.easyexcel.listener.ReadDataListener;

public class ReadExcelExample {

  private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "test";

  public static void simpleRead() {
    File file = new File(BASE_PATH, "staff.xlsx");
    Consumer<List<Staff>> consumer = staffList -> System.out.println("读到的数据为：" + staffList);
    EasyExcel.read(file, Staff.class, new ReadDataListener(consumer)).sheet(0).doRead();
  }

  public static void main(String[] args) {
    simpleRead();
  }
}
