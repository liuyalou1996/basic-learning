package com.universe.thirdparty.easyexcel.example.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;

public class Student {

  @ExcelProperty(index = 1, value = "学号")
  private int age;

  @ExcelProperty(index = 0, value = "姓名")
  private String name;

  @ExcelProperty(index = 2, value = "总分")
  private double score;

  @ExcelProperty(index = 3, value = "考试日期")
  @DateTimeFormat("yyyy-MM-dd")
  private Date examDate;

  @ExcelIgnore
  private String remark;

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public Date getExamDate() {
    return examDate;
  }

  public void setExamDate(Date examDate) {
    this.examDate = examDate;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override
  public String toString() {
    return "Student [age=" + age + ", name=" + name + ", score=" + score + ", examDate=" + examDate + ", remark="
        + remark + "]";
  }

}
