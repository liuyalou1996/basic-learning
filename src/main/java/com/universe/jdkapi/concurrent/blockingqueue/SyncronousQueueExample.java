package com.universe.jdkapi.concurrent.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 特性:
 * 1、插入和删除操作交替执行，当另一个线程执行插入操作后当前线程才能执行删除操作，否则当前线程会阻塞;
 * 2、当数据被另一个线程执行删除操作后当前线程才能执行插入操作，否则当前线程会阻塞。
 * 3、SyschromousQueue无容量，只有当执行删除操作即调用take()方法才能获取插入的元素，调用peek()会返回空值。
 * 4、可选的公平策略，如果设为true，则线程以FIFO的顺序访问;
 */
public class SyncronousQueueExample {

  static class ProducerThread extends Thread {

    private BlockingQueue<Integer> queue;

    public ProducerThread(BlockingQueue<Integer> queue) {
      this.queue = queue;
    }

    @Override
    public void run() {
      for (int count = 0; count <= 10; count++) {
        System.out.println(super.getName() + "生产者准备放置元素 ==> " + count + ",当前时间为:" + System.nanoTime());
        try {
          queue.put(count);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(super.getName() + "元素放置成功 ==> " + count + ",当前时间为:" + System.nanoTime());
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
      for (int count = 0; count <= 10; count++) {
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
    BlockingQueue<Integer> queue = new SynchronousQueue<>();
    ProducerThread producerThread = new ProducerThread(queue);
    ConsumerThread consumerThread = new ConsumerThread(queue);
    producerThread.start();
    consumerThread.start();
  }

}
