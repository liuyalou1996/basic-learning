package com.universe.jdkapi.thread;

class YieldThread extends Thread {

  public YieldThread(String name) {
    super(name);
  }

  @Override
  public void run() {
    for (int i = 0; i < 20; i++) {
      System.out.println(super.getName() + " " + i);
      if (i == 10) {
        Thread.yield();
      }
    }
  }
}

/**
 * <h2>Thread.sleep和Thread.yield的区别</h2>
 * <ul>
 *    <li>sleep方法暂停当前线程后，会给其它线程执行机会，不会理会其它线程的优先级；但yield方法只会给优先级相同或优先级更高的线程执行机会</li>
 *    <li>sleep方法会将线程转入阻塞状态，直到经过阻塞时间才会进入就绪状态；而yield不会将线程转入阻塞状态，只是强制当前线程进入就绪状态，因此完全有可能某个线程调用yield方法暂停之后，
 *    立即再次获得处理器资源执行</li>
 *    <li>sleep方法声明抛出了InteruptedException，所以调用sleep方法要么捕捉异常，要么显示声明抛出异常；而yield方法则没有声明抛出异常</li>
 *    <li>sleep方法比yield方法有更好的可移植性，不建议使用yield方法控制并发线程的执行</li>
 * </ul>
 */
public class ThreadYieldExample {

  public static void main(String[] args) {
    YieldThread highPriorityThread = new YieldThread("高优先级线程");
    highPriorityThread.setPriority(Thread.MAX_PRIORITY);
    highPriorityThread.start();

    YieldThread lowPriorityThread = new YieldThread("低优先级线程");
    lowPriorityThread.setPriority(Thread.MIN_PRIORITY);
    lowPriorityThread.start();

  }
}
