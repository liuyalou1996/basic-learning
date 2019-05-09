package com.universe.middleware.zk.curator.recipes;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * InterProcessMutex是可重入的互斥锁，而且这种锁是公平的，即会根据请求的顺序获取
 */
public class InterProcessMutexExample {

  private static CuratorFramework client;
  private static InterProcessMutex lock;

  static {
    client = CuratorFrameworkFactory.newClient("localhost:2181", new ExponentialBackoffRetry(1000, 3));
    client.start();

    lock = new InterProcessMutex(client, "/mutexLock");
  }

  public static void obtainLockAndExecute() throws Exception {
    // 指定时间内获取锁
    if (lock.acquire(0, TimeUnit.MILLISECONDS)) {
      try {
        System.out.println(Thread.currentThread().getName() + "获取执行权!");
        // 睡眠2秒
        Thread.sleep(500);
      } catch (Exception e) {
        throw e;
      } finally {
        // 如果获得锁，则释放锁
        lock.release();
      }

    }
  }

  public static void main(String[] args) throws Exception {
    CountDownLatch latch = new CountDownLatch(5);

    Runnable task = () -> {
      try {
        obtainLockAndExecute();
        latch.countDown();
      } catch (Exception e) {
        e.printStackTrace();
      }
    };

    for (int i = 0; i < 5; i++) {
      new Thread(task, "线程" + i).start();
    }

    latch.await();
    // 等待所有任务执行完关闭客户端
    client.close();
  }
}
