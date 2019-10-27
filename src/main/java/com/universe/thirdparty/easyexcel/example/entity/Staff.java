package com.universe.thirdparty.easyexcel.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.excel.annotation.ExcelProperty;

public class Staff {

  @ExcelProperty(index = 0)
  private String name;

  @ExcelProperty(index = 1)
  private Integer age;

  @ExcelProperty(index = 2)
  private String phone;

  @ExcelProperty(index = 3)
  private double salary;

  @ExcelProperty(index = 4)
  private BigDecimal bonus;

  @ExcelProperty(index = 5)
  private Date entryDate;

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public String getPhone() {
    return phone;
  }

  public double getSalary() {
    return salary;
  }

  public BigDecimal getBonus() {
    return bonus;
  }

  public Date getEntryDate() {
    return entryDate;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public void setBonus(BigDecimal bonus) {
    this.bonus = bonus;
  }

  public void setEntryDate(Date entryDate) {
    this.entryDate = entryDate;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
