package com.iboxpay.jdkapi.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

  public static void main(String[] args) throws Exception {
    CountDownLatch latch = new CountDownLatch(1);
    new Thread(() -> {
      try {
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + "线程已完成操作!");
        // 计数器减数，当数值为0时释放所有等待的线程
        latch.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
    latch.await();
    System.out.println(Thread.currentThread().getName() + "线程执行操作!");
  }
}
