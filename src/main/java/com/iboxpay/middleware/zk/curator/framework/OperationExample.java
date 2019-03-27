package com.iboxpay.middleware.zk.curator.framework;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class OperationExample {

  private static final CuratorFramework client;

  static {
    ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
    client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
  }

  public static void createPersistantNode(String path, byte[] data) throws Exception {
    client.create().forPath(path, data);
  }

  public static void createEphemeralNode(String path, byte[] data) throws Exception {
    client.create().withMode(CreateMode.EPHEMERAL).forPath(path, data);
  }

  public static void createEphemeralSequentialNode(String path, byte[] data) throws Exception {
    // 由于节点创建成功可能没有返回，使用保护模式创建时节点名会以GUID为前缀，重试时找带有GUID的父节点，如果找到则为丢失的节点
    client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data);
  }

  public static void setData(String path, byte[] data) throws Exception {
    client.setData().forPath(path, data);
  }

  public static void setDataAsync(String path, byte[] data) throws Exception {
    CuratorListener listener = (client, event) -> {
      System.out.println(event.getPath() + "成功设置数据");
    };
    client.getCuratorListenable().addListener(listener);

    // 异步设置节点数据，CuratorListener会通知结果
    client.setData().inBackground().forPath(path, data);
  }

  public static void setDataAsyncWithCallback(String path, byte[] data, BackgroundCallback callback) throws Exception {
    client.setData().inBackground(callback).forPath(path, data);
  }

  public static void delete(String path) throws Exception {
    // 递归删除
    client.delete().deletingChildrenIfNeeded().forPath(path);
  }

  public static void guaranteeDelete(String path) throws Exception {
    // 解决极端情况，删除时可能操作完成但可能由于连接丢失响应未返回给客户端
    client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
  }

  public static String getDataWithWatcher(String path, CuratorWatcher watcher) throws Exception {
    byte[] data = client.getData().usingWatcher(watcher).forPath(path);
    return new String(data, StandardCharsets.UTF_8);
  }

  public static List<String> getChildrenWithWatcher(String path) throws Exception {
    // watch的记过会通过setDataAsync()时指定的CuratorListener来通知
    return client.getChildren().watched().forPath(path);
  }

  public static List<String> getChildrenWithWatcher(String path, Watcher watcher) throws Exception {
    return client.getChildren().usingWatcher(watcher).forPath(path);
  }

  public static void startClient() {
    client.start();
  }

  public static void closeClient() {
    client.close();
  }

  private static void processEvent(WatchedEvent event) {
    EventType type = event.getType();
    if (EventType.NodeDeleted == type) {
      System.out.println(event.getPath() + "节点已经被删除!");
    } else if (EventType.NodeCreated == type) {
      System.out.println(event.getPath() + "节点已经被创建!");
    } else if (EventType.NodeDataChanged == type) {
      System.out.println(event.getPath() + "节点数据变化!");
    } else if (EventType.NodeChildrenChanged == type) {
      System.out.println(event.getPath() + "该节点的子节点变化!");
    }
  }

  public static void main(String[] args) throws Exception {
    Watcher watcher = event -> {
      processEvent(event);
    };

    CuratorWatcher curatorWatcher = event -> {
      processEvent(event);
    };

    // 操作前必须启动客户端
    startClient();

    createPersistantNode("/curator", "root node".getBytes());
    createPersistantNode("/curator/node", "children node".getBytes());

    String data = getDataWithWatcher("/curator/node", curatorWatcher);
    System.out.println("/curator/node节点下的数据为：" + data);
    List<String> children = getChildrenWithWatcher("/curator", watcher);
    System.out.println("/curator下的根节点为：" + children);

    delete("/curator");

    // 操作完成关闭客户端
    closeClient();
  }

}
