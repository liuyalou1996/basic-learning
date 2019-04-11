package com.iboxpay.jdkapi.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

class PrintTask extends RecursiveAction {

  private static final long serialVersionUID = -736056019681893675L;
  /**
   * 每个小任务最多只打印50个数
   */
  private static final int THRESHOLD = 50;
  private int start;
  private int end;

  public PrintTask(int start, int end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected void compute() {
    if (end - start < THRESHOLD) {
      for (int i = start; i < end; i++) {
        System.out.println(Thread.currentThread().getName() + "的i值为：" + i);
      }
    } else {
      int mid = (start + end) / 2;
      PrintTask left = new PrintTask(start, mid);
      PrintTask right = new PrintTask(mid, end);

      // 并行执行两个任务
      left.fork();
      right.fork();
    }
  }

}

class ComputationTask extends RecursiveTask<Integer> {

  private static final long serialVersionUID = -7320040402408005655L;

  private static final int THRESHOLD = 20;
  private int[] arr;
  private int start;
  private int end;

  public ComputationTask(int[] arr, int start, int end) {
    this.arr = arr;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Integer compute() {
    int sum = 0;
    // 当end与start之间的差小于THRESHOLD时，开始进行实际累加
    if (end - start < THRESHOLD) {
      for (int i = start; i < end; i++) {
        sum += arr[i];
      }
    } else {
      // 任务拆解，将大任务拆分为小任务
      int mid = (start + end) / 2;
      ComputationTask left = new ComputationTask(arr, start, mid);
      ComputationTask right = new ComputationTask(arr, mid, end);

      // 并行执行两个任务
      left.fork();
      right.fork();

      // 把两个任务的结果加起来
      sum = left.join() + right.join();
    }

    return sum;
  }

}

public class ForkJoinExample {

  public static void recursiveActionTest() throws InterruptedException {
    // ForkJoinPool是一种特殊的线程池，支持将一个任务拆分成多个小任务并行计算，再把多个小任务合并成总的计算结果，充分利用多CPU、多核CPU的优势
    ForkJoinPool pool = new ForkJoinPool();
    pool.submit(new PrintTask(0, 300));
    // 阻塞，直到shutdown或者超时
    pool.awaitTermination(2, TimeUnit.SECONDS);
    // 关闭线程池
    pool.shutdown();
  }

  public static void recursiveTaskTest() throws Exception {
    int[] arr = new int[100];
    for (int count = 0; count < arr.length; count++) {
      arr[count] = count;
    }

    // 通用pool，静态构造，不会受shutdown和shutdownNow方法影响
    ForkJoinPool pool = ForkJoinPool.commonPool();
    // ForkJoinTask实现了Future接口
    Future<Integer> result = pool.submit(new ComputationTask(arr, 0, arr.length));
    System.out.println("计算结果为：" + result.get());
    pool.shutdown();
  }

  public static void main(String[] args) throws Exception {
    // recursiveActionTest();
    recursiveTaskTest();
  }

}
