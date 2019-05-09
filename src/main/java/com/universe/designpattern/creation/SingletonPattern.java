package com.universe.designpattern.creation;

class Singleton {

  // volatile关键字保证可见性和禁止指令重排
  private static volatile Singleton instance;

  private Singleton() {

  }

  public static Singleton getInstance() {
    // 最外层循环为空判断提高性能，防止每个线程进入该方法都要获取锁
    if (instance == null) {
      synchronized (Singleton.class) {
        // 这里非空判断保证单例
        if (instance == null) {
          instance = new Singleton();
        }
      }
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
