package com.universe.jdkapi.threadpool;

/**
 * ThreadLocal意为线程局部变量，为每一个使用该变量的线程都提供一个变量值的副本，使每一个线程都可以独立地改变自己的副本，而不会和其它线程的副本冲突
 */
public class ThreadLocalExample {

  private static ThreadLocal<String> holder = new ThreadLocal<>();

  public static void main(String[] args) {

    Runnable task = () -> {
      String name = Thread.currentThread().getName();
      holder.set(name);
      System.out.println(name + "绑定的线程局部变量为：" + holder.get());
    };

    new Thread(task, "线程甲").start();
    new Thread(task, "线程乙").start();

    // 主线程获取不到变量副本
    System.out.println(Thread.currentThread().getName() + "绑定的线程局部变量为：" + holder.get());

  }

}
