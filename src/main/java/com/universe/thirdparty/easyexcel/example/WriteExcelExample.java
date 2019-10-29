package com.universe.thirdparty.easyexcel.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.universe.jdkapi.jdk8.datetime.DateUtils;
import com.universe.jdkapi.jdk8.datetime.DateUtils.Pattern;
import com.universe.thirdparty.easyexcel.EasyExcelUtils;
import com.universe.thirdparty.easyexcel.example.entity.Student;

public class WriteExcelExample {

  private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "test" + File.separator + "temp";

  private static List<Student> studentList = new ArrayList<>();

  static {
    Student student = new Student();
    student.setName("小明");
    student.setAge(22);
    student.setScore(759.65);
    student.setExamDate(new Date());
    student.setRemark("无");

    for (int count = 0; count < 10; count++) {
      studentList.add(student);
    }
  }

  public static void simpleWrite() {
    File file = new File(BASE_PATH, generateFileName());
    // 写之前上级目录一定要存在，不然会报错
    EasyExcel.write(file).head(Student.class).sheet("模板").doWrite(studentList);
  }

  /**
   * 单次全部填充
   */
  public static void fillTemplate() {
    File template = new File(BASE_PATH, "student.xlsx");
    File dest = new File(BASE_PATH, generateFileName());
    // 指定head后会写入头部信息，需要手动进行忽略
    EasyExcel.write(dest).head(Student.class).withTemplate(template).needHead(false).sheet("模板").doWrite(studentList);
  }

  /**
   * 分多次根据模板填充，会使用文件缓存
   */
  public static void batchFillTemplate() {
    File template = new File(BASE_PATH, "student.xlsx");
    File dest = new File(BASE_PATH, generateFileName());

    ExcelWriter writer = EasyExcel.write(dest).withTemplate(template).build();
    WriteSheet sheet = EasyExcel.writerSheet(0).head(Student.class).needHead(false).build();
    writer.write(studentList.subList(0, 5), sheet);
    writer.write(studentList.subList(5, 10), sheet);
    // 写完后关闭流
    writer.finish();
  }

  private static String generateFileName() {
    return DateUtils.format(LocalDateTime.now(), Pattern.DATE_TIME_MILLS_WITHOUT_STRIKE) + ".xlsx";
  }

  public static void main(String[] args) throws FileNotFoundException {
    File template = new File(BASE_PATH, "student.xlsx");
    File dest = new File(BASE_PATH, generateFileName());
    EasyExcelUtils.batchFillSheet(dest, template, studentList, 1, 2);
  }
}
