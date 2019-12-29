package com.universe.jdkapi.concurrent.visibility;

public class VisibilityExample {

  private static boolean init;
  private static int number;

  public static void keepVisibilityWithVolatile() throws InterruptedException {
    new Thread(() -> {
      while (!init) {

      }

      System.out.println(number);
    }).start();

    Thread.sleep(2000L);
    // 如果不加volatile关键字，共享变量的值不会同步回主存，程序将一直循环
    number = 42;
    init = true;
  }

  public static void keepVisibilityWithSynchronized() throws InterruptedException {
    new Thread(() -> {
      while (!init) {
        synchronized (VisibilityExample.class) {

        }
      }
      System.out.println(number);
    }).start();

    Thread.sleep(2000L);
    // 如果不加volatile关键字，共享变量的值不会同步回主存，程序将一直循环
    number = 42;
    init = true;
  }

  public static void main(String[] args) throws InterruptedException {
    keepVisibilityWithSynchronized();
  }

}
