package com.iboxpay.thirdparty.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

class Student {

  private String name;
  private Integer age;

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Student [name=" + name + ", age=" + age + "]";
  }

}

public class ProtostuffTest {

  public static void main(String[] args) {
    Student student = new Student();
    student.setName("lyl");
    student.setAge(20);
    Schema<Student> schema = RuntimeSchema.getSchema(Student.class);
    LinkedBuffer buffer = LinkedBuffer.allocate();
    try {
      byte[] bytes = ProtostuffIOUtil.toByteArray(student, schema, buffer);
      Student message = schema.newMessage();
      ProtostuffIOUtil.mergeFrom(bytes, message, schema);
      System.out.println(message);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage(), e);
    } finally {
      buffer.clear();
    }

  }

}
