package com.universe.thirdparty.easyexcel.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
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
    File file = new File(BASE_PATH, System.currentTimeMillis() + ".xlsx");
    // 写之前上级目录要存在
    EasyExcel.write(file).head(Student.class).sheet("模板").doWrite(studentList);
  }

  public static void main(String[] args) {
    simpleWrite();
  }
}
