package com.universe.jdkapi.thread;

class JoinThread extends Thread {

  public JoinThread(String name) {
    super(name);
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      System.out.println(super.getName() + "" + i);
    }
  }

}

public class ThreadJoinExample {

  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 100; i++) {
      if (i == 20) {
        JoinThread jt = new JoinThread("被Join的线程");
        jt.start();
        // 主线程调用了jt线程的join方法，主线程将会等待，直到jt线程执行完
        jt.join();
      }

      System.out.println(Thread.currentThread().getName() + " " + i);
    }
  }
}
