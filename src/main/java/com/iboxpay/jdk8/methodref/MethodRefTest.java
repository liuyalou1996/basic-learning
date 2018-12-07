package com.iboxpay.jdk8.methodref;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.iboxpay.jdk8.methodref.CarFactory.Car;

interface MethodRef {

  public String substring(String str, int start, int end);
}

class CarFactory {

  public static class Car {

    private String brand;

    public String getBrand() {
      return brand;
    }

    public void setBrand(String brand) {
      this.brand = brand;
    }

    public void description() {
      System.out.println("I'm a car.");
    }

    @Override
    public String toString() {
      return "Car [brand=" + brand + "]";
    }
  }

  public static void sell(Car car) {
    System.out.println("we are selling a high-performance car:" + car.getBrand() + ".");
  }

  public Car createCar(Supplier<Car> supplier) {
    return supplier.get();
  }

  public void repair(Car car) {
    System.out.println("The car " + car.getBrand() + " doesn't work well,need repair.");
  }

}

/*
 *当Lambda表达式语句只有一条时，可以使用方法引用，主要有构造器引用，实例方法引用，特定类的对象方法引用
 */
public class MethodRefTest {

  private static CarFactory factory = new CarFactory();
  private static List<Car> carList;

  static {
    Car car = new CarFactory().createCar(Car::new);
    car.setBrand("bnw");
    carList = Arrays.asList(car);
  }

  /**
   * 构造器调用
   */
  public static void invokeContructor() {
    Car car = factory.createCar(Car::new);
    car.setBrand("benz");
    System.out.println(car.getBrand());
  }

  /**
   * 静态方法调用
   */
  public static void invokeStaticMethod() {
    carList.forEach(CarFactory::sell);
  }

  /**
   * 特定类的对象方法调用，函数式接口的第一个参数为调用者，后面的参数作为调用者方法的参数
   */
  public static void invokeSpecifiedClassInstanceMethod() {
    MethodRef methodRef = String::substring;
    String str = methodRef.substring("abcdefg", 0, 3);
    System.out.println(str);
  }

  /**
   * 实例方法调用
   */
  public static void invokeInstanceMethod() {
    carList.forEach(factory::repair);
  }

  public static void main(String[] args) {
    invokeContructor();
    invokeStaticMethod();
    invokeSpecifiedClassInstanceMethod();
    invokeInstanceMethod();
  }
}
