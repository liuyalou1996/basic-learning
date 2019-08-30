package com.universe.jdkapi.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 特性：
 * 1、有界限的阻塞队列，队列中的元素顺序为FIFO，队列头部的元素为入队时间最久的，队列尾部的元素为入队时间最晚的;
 * 2、元素在队列尾部插入，在队列头部删除;
 * 3、可选的公平策略，如果设为true，则线程以FIFO的顺序访问;
 */
public class ArrayBlockingQueueExample {

  static class ProducerThread extends Thread {

    private BlockingQueue<Integer> queue;

    public ProducerThread(BlockingQueue<Integer> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      for (int count = 0; count < 10; count++) {
        System.out.println(super.getName() + "生产者准备生产元素 ==> " + count + ",当前时间为:" + System.nanoTime());
        try {
          queue.put(count);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(super.getName() + "元素生产成功 ==> " + count + ",当前时间为:" + System.nanoTime());
      }
    }

  }

  static class ConsumerThread extends Thread {

    private BlockingQueue<Integer> queue;

    public ConsumerThread(BlockingQueue<Integer> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      for (int count = 0; count < 10; count++) {
        System.out.println(super.getName() + "消费者准备取出元素，当前时间为:" + System.nanoTime());
        try {
          System.out.println(super.getName() + "取出的元素为 ==> " + queue.take() + ",当前时间为:" + System.nanoTime());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args) {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
    ProducerThread producerThread = new ProducerThread(queue);
    ConsumerThread consumerThread = new ConsumerThread(queue);
    producerThread.start();
    consumerThread.start();
  }
}
