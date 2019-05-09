package com.universe.jdkapi.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Account {

  private double balance = 10000;

  private ReentrantLock lock = new ReentrantLock();

  private Condition condition = lock.newCondition();

  public void deposite(double amount) {
    try {
      lock.lock();
      while (true) {
        System.err.println("存钱线程是否拥有锁:" + lock.isHeldByCurrentThread());
        if (balance + amount > 10000) {
          System.out.println("账户余额已满," + Thread.currentThread().getName() + "不能往账户存钱!");
          condition.await();
        } else {
          balance += amount;
          Thread.sleep(500);
          System.out.println(Thread.currentThread().getName() + "往账户存钱，账户余额为：" + balance);
          condition.signalAll();
          break;
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public void withdraw(double amount) {
    try {
      lock.lock();
      while (true) {
        if (balance - amount < 0) {
          System.out.println("账户余额不足," + Thread.currentThread().getName() + "不能从账户取钱!");
          condition.await();
        } else {
          Thread.sleep(500);
          balance -= amount;
          System.out.println(Thread.currentThread().getName() + "从账户取钱，账户余额为：" + balance);
          condition.signalAll();
          break;
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }
}

public class ReentrantLockTest {

  public static void main(String[] args) {
    Account account = new Account();
    Runnable deposite = () -> {
      account.deposite(1000);
    };
    Runnable withdraw = () -> {
      account.withdraw(1000);
    };

    for (int count = 0; count < 10; count++) {
      if (count % 2 == 0) {
        new Thread(deposite).start();
      } else {
        new Thread(withdraw).start();
      }
    }
  }

}
