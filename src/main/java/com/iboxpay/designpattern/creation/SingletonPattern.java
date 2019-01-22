package com.iboxpay.designpattern.creation;

class Singleton {

  private static Singleton instance;

  private Singleton() {

  }

  public static synchronized Singleton getInstance() {
    if (instance == null) {
      instance = new Singleton();
    }

    return instance;
  }
}

/**
 * 单例模式分为懒汉式和饿汉式，两者的相同点为构造器都是私有的，不同点为饿汉式单例会在类加载的时候实例化，而懒汉式单例则在第一次方法调用时实例化，且获取实例方法需要加上同步锁
 */
public class SingletonPattern {

  public static void main(String[] args) {
    Singleton instance = Singleton.getInstance();
    Singleton instance2 = Singleton.getInstance();
    System.out.println(instance == instance2);
  }

}
