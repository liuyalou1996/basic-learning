package com.universe.jdkapi.concurrent.tool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

  public static void main(String[] args) {
    CyclicBarrier barrier = new CyclicBarrier(5, () -> {
      System.out.println("所有线程已执行完毕," + Thread.currentThread().getName() + "执行传入的barrierAction");
    });

    Runnable task = () -> {
      try {
        System.out.println(Thread.currentThread().getName() + "开始执行操作!");
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + "已执行完操作!");
        // 当所有线程在Barrier上等待，Barrier会被trip掉，传入的barrierAction会被当中一个线程执行
        barrier.await();
        System.out.println(Thread.currentThread().getName() + "等待的线程已执行完毕!");
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
    };
    // 开启5个线程，每个线程执行完后要等待其它所有线程执行完才能往后执行，否则会被挂起，CyclicBarrier能被再次使用
    for (int count = 0; count < 5; count++) {
      new Thread(task).start();
    }
  }
}
