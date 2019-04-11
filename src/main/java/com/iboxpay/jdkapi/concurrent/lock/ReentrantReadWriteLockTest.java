package com.iboxpay.jdkapi.concurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁维护着一对锁，一把用于读操作，另一把用于写操作，读锁能被多个读线程拥有，而写锁是互斥的，只能被一个线程拥有<br/>
 * 锁降级：从写锁变成读锁。<br/>
 * 锁升级：从读锁变成写锁。<br/>
 * ReentrantReadWriteLock只支持锁降级，不支持锁升级，写锁的并发限制要比读锁高。<br/>
 * ReentrantReadWriteLock也支持Condition，但只限于写锁。
 */
public class ReentrantReadWriteLockTest {

  /**
   * 读锁变写锁时必须释放读锁，否则锁升级会导致死锁
   */
  public static void upgradingTest() {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    lock.readLock().lock();
    // lock.readLock().unlock();
    lock.writeLock().lock();
  }

  /**
   * 锁降级正确顺序为先获取写锁，再获取读锁，然后再释放写锁，最后再释放读锁
   */
  public static void downgradingTest() {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    lock.writeLock().lock();
    lock.readLock().lock();
    lock.writeLock().unlock();
  }

  static class CacheDemo {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private Map<String, Object> buffer = new HashMap<>();

    public Object get(String key) {
      // 先获取读锁
      lock.readLock().lock();
      Object value = null;
      if (buffer.get(key) != null) {
        value = buffer.get(key);
      }

      // 释放读锁，获取写锁
      lock.readLock().unlock();
      lock.writeLock().lock();
      value = "liuyalou";
      buffer.put(key, value);

      // 锁降级，获取读锁，释放写锁，再释放读锁
      lock.readLock().lock();
      lock.writeLock().unlock();
      lock.readLock().unlock();

      return value;
    }
  }

  public static void main(String[] args) {
    CacheDemo demo = new CacheDemo();
    System.out.println(demo.get("name"));
  }

}
