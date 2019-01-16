package com.iboxpay.jdkapi.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

  public static void main(String[] args) throws Exception {
    // 用来限制访问资源的线程数量，如果permit不可用则调用acquire方法会阻塞
    Semaphore semaphore = new Semaphore(5);
    Runnable task = () -> {
      try {
        // 从Semaphore中获取一个许可
        semaphore.acquire();
        System.out.println(Thread.currentThread().getName() + "从Semaphore中获取到一个许可!");
        // 等待一段时间后释放许可
        Thread.sleep(5000);
        semaphore.release();
        System.out.println(Thread.currentThread().getName() + "释放一个许可并返回到Semaphore中!");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    for (int count = 0; count < 8; count++) {
      new Thread(task).start();
    }
  }

}
