package com.iboxpay.jdk8.methodref;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

class Car {

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

class CarFactory {

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
 *方法引用测试 
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
   * 特定类的对象方法调用，不能带参数，由于list中存的是car，所以只能调用Car类对象的无参方法
   */
  public static void invokeGeneralInstanceMethod() {
    carList.forEach(Car::description);
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
    invokeGeneralInstanceMethod();
    invokeInstanceMethod();
  }
}
