package com.universe.middleware.zk.client;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributedDispatcher implements Watcher {

  private Logger logger = LoggerFactory.getLogger("distributedLDispatcher");

  // 连接zooKeeper默认会话时长
  private static final int DEFAULT_SESSION_TIMEOUT = 3 * 1000;
  private static final String CONCAT_STR = "_";

  // 会话时长
  private int sessionTimeout;
  // 任务名，作为根节点使用
  private String taskName;
  private String rootNode;
  // 当前节点
  private String currentNode;
  // zooKeeper客户端
  private ZooKeeper zk;
  // 计数器
  private CountDownLatch latch = new CountDownLatch(1);

  public DistributedDispatcher(String connectionStr, String taskName, int sessionTimeout) {
    this.taskName = taskName;
    this.rootNode = "/" + taskName;
    this.sessionTimeout = sessionTimeout;
    try {
      // 连接到zooKeeper服务器
      zk = new ZooKeeper(connectionStr, this.sessionTimeout, this);
      Stat stat = zk.exists(rootNode, false);
      if (stat == null) {
        // 如果根节点不存在,则创建根节点
        zk.create(rootNode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
      }
    } catch (IOException e) {
      logger.error("与服务器建立连接失败:{} " + e.toString());
    } catch (KeeperException e) {
      if (e instanceof KeeperException.NodeExistsException) {
        logger.error("节点已存在:{} " + e.toString());
      }
    } catch (InterruptedException e) {
      logger.error("线程被打断异常:{} " + e.toString());
    }
  }

  public DistributedDispatcher(String connectionStr, String taskName) {
    this(connectionStr, taskName, DEFAULT_SESSION_TIMEOUT);
  }

  public boolean isAvailable() throws InterruptedException, KeeperException {
    // 创建临时顺序节点，节点名称为：任务名_序列号
    currentNode = zk.create(rootNode + "/" + taskName + CONCAT_STR, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
        CreateMode.EPHEMERAL_SEQUENTIAL);
    logger.info(currentNode + "节点创建成功");
    // 取当前节点下的所有子节点
    List<String> children = zk.getChildren(rootNode, false);
    // 节点根据序列号从小到大排序
    Collections.sort(children);
    // 如果当前节点为最小节点，则获得执行权
    if (currentNode.equals(rootNode + "/" + children.get(0))) {
      return true;
    }
    return false;
  }

  /**
   * 一段时间后释放执行权，
   * @param timeout 释放等待时长,由于系统时间差，A机器执行完后，B机器可能还没有执行，出现节点删除过早的情况
   * @param timeUnit 时长单位
   * @throws InterruptedException 
   * @throws KeeperException 
   */
  public void release(long timeout, TimeUnit timeUnit) throws InterruptedException, KeeperException {
    // 等待
    latch.await(timeout, timeUnit);
    // 删除节点
    zk.delete(currentNode, -1);
    zk.close();
    logger.info("删除节点" + currentNode + "成功");
  }

  @Override
  public void process(WatchedEvent event) {

  }

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    Runnable task3 = () -> {
      DistributedDispatcher dispatcher = new DistributedDispatcher("localhost:2181", "task3");
      try {
        if (dispatcher.isAvailable()) {
          System.out.println(Thread.currentThread().getName() + "获得task3执行权");
          System.out.println(Thread.currentThread().getName() + "执行定时任务task3");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (dispatcher != null) {
            dispatcher.release(3000, TimeUnit.MILLISECONDS);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    Runnable task2 = () -> {
      DistributedDispatcher dispatcher = new DistributedDispatcher("localhost:2181", "task2");
      try {
        if (dispatcher.isAvailable()) {
          System.out.println(Thread.currentThread().getName() + "获得task2执行权");
          System.out.println(Thread.currentThread().getName() + "执行定时任务task2");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (dispatcher != null) {
            dispatcher.release(10000, TimeUnit.MILLISECONDS);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    new Thread(task3, "B机器").start();
    new Thread(task3, "A机器").start();
    // new Thread(task2, "A机器").start();
    // new Thread(task2, "B机器").start();
  }
}
