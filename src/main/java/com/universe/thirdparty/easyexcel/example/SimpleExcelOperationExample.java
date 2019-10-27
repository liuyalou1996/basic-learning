package com.universe.thirdparty.easyexcel.example;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.universe.thirdparty.easyexcel.example.entity.Staff;
import com.universe.thirdparty.easyexcel.example.listener.ReadExcelListener;

public class SimpleExcelOperationExample {

  private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "test";

  public static void readSingleExcel() {
    File file = new File(BASE_PATH, "staff.xlsx");
    Consumer<List<Staff>> consumer = staffList -> System.out.println("读到的数据为：" + staffList);
    // 默认读取第一个sheet，表头为第一行
    EasyExcel.read(file, Staff.class, new ReadExcelListener(consumer)).sheet(0).doRead();
  }

  public static void readMultipleSameExcel() {
    File file = new File(BASE_PATH, "staff.xlsx");
    Consumer<List<Staff>> consumer = staffList -> System.out.println("读到的数据为：" + staffList);
    // 默认读取第一个sheet
    ExcelReader reader = EasyExcel.read(file).autoTrim(true).build();
    ReadSheet firstSheet =
        EasyExcel.readSheet(0).head(Staff.class).registerReadListener(new ReadExcelListener(consumer)).build();
    ReadSheet secondSheet =
        EasyExcel.readSheet(1).head(Staff.class).registerReadListener(new ReadExcelListener(consumer)).build();
    reader.read(firstSheet);
    reader.read(secondSheet);
    reader.finish();
  }

  public static void readSynchronously() {
    File file = new File(BASE_PATH, "staff.xlsx");
    // 不指定head则list中的object为Map<Integer,String>，即key为第几列，值为对应列的值
    List<Object> list = EasyExcel.read(file).head(Staff.class).sheet(0).doReadSync();
    System.out.println(list);
  }
}
