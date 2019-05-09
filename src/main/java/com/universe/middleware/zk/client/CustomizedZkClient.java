package com.universe.middleware.zk.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.universe.thirdparty.fastjson.CollectionUtils;

public class CustomizedZkClient implements Watcher {

  private Logger logger = LoggerFactory.getLogger(CustomizedZkClient.class);

  private static final int SESSION_TIMEOUNT = 5 * 1000;

  /**
   * 默认ACL，所有人都可访问
   */
  private static final ArrayList<ACL> DEFAULT_ACL = ZooDefs.Ids.OPEN_ACL_UNSAFE;

  /**
   * 节点默认创建模式为永久节点
   */
  private static final CreateMode PERSISTENT = CreateMode.PERSISTENT;

  private ZooKeeper zk;

  @Override
  public void process(WatchedEvent event) {
    if (event.getState() == KeeperState.SyncConnected) {}
    System.out.println("=========当前zooKeeper的状态信息============");
    System.out.println("与事件相关联的znode的路径:" + event.getPath());
    System.out.println("当前状态信息:" + event.getState());
    System.out.println("========================================");
  }

  // 连接到zookeeper服务器
  public void connect(String connectionStr) {
    // 通过传入连接字符串和会话时长以及watcher实例，注意连接字符串以逗号分隔，连接字符串为地址:端口的形式，客户端会从连接字符串
    // 随机选择服务器并连接，如果连接失败，将会从选择其它服务器进行连接
    try {
      zk = new ZooKeeper(connectionStr, SESSION_TIMEOUNT, this);
    } catch (IOException e) {
      logger.error("与服务建立连接失败,错误信息:{}" + e.getMessage());
    }
  }

  /**
   * 创建节点
   * @param path 节点路径
   * @param data 节点数据
   * @param acl 访问控制列表，通过ZooDefs.Ids指定
   * @param mode 节点创建模式，通过CreateMode指定枚举值
   * @return
   */
  public String createZnode(String path, String data, ArrayList<ACL> acl, CreateMode mode) {
    String nodePath = null;
    try {
      if (CollectionUtils.isEmpty(acl) && mode == null) {
        nodePath = zk.create(path, data.getBytes(), DEFAULT_ACL, PERSISTENT);
      } else if (!CollectionUtils.isEmpty(acl)) {
        nodePath = zk.create(path, data.getBytes(), acl, PERSISTENT);
      } else if (mode != null) {
        nodePath = zk.create(path, data.getBytes(), DEFAULT_ACL, mode);
      }
      logger.info("节点创建成功,路径为:" + path + ",数据为:" + data);
    } catch (KeeperException | InterruptedException e) {
      logger.error("节点创建失败，错误信息为:{}" + e.getMessage());
    }
    return nodePath;
  }

  public String createZnode(String path, String data) {
    return createZnode(path, data, null, null);
  }

  // 删除节点
  public boolean deleteZnode(String path) {
    try {
      // -1代表匹配任意版本的节点
      zk.delete(path, -1);
      return true;
    } catch (InterruptedException | KeeperException e) {
      logger.error("删除节点" + path + "失败,错误信息信息为:" + e.getMessage());
    }
    return false;
  }

  // 为节点设置数据
  public boolean setData(String path, String data) {
    try {
      zk.setData(path, data.getBytes(), -1);
    } catch (KeeperException | InterruptedException e) {
      logger.error("为节点" + path + "设置数据:" + data + ",失败");
    }
    return false;
  }

  // 从节点上获取数据
  public String getData(String path, boolean isWatched) {
    String data = null;
    try {
      data = new String(zk.getData(path, isWatched, null), "utf-8");
    } catch (KeeperException | InterruptedException | UnsupportedEncodingException e) {
      logger.error("从" + path + "节点上获取数据失败,错误信息为:{}" + e.getMessage());
    }
    return data;
  }

  // 从节点上获取子节点
  public List<String> getChildren(String path, boolean isWatched) {
    List<String> children = null;
    try {
      children = zk.getChildren(path, isWatched);
    } catch (KeeperException | InterruptedException e) {
      logger.error("从" + path + "节点上获取子节点失败,错误信息为:{}" + e.getMessage());
    }
    return children;
  }

  // 判断节点是否存在
  public boolean isExisted(String path, boolean isWatched) {
    Stat stat = null;
    try {
      stat = zk.exists(path, isWatched);
    } catch (KeeperException | InterruptedException e) {
      logger.error("该节点不存在,错误信息为:{}" + e.getMessage());
    }
    return stat != null;
  }

  public void close() {
    try {
      if (null != zk) {
        zk.close();
      }
    } catch (InterruptedException e) {
      logger.info("与服务器断开连接出现异常,错误信息为:{}" + e.getMessage());
    }
  }

  public static void main(String[] args) throws Exception {
    CustomizedZkClient client = new CustomizedZkClient();
    client.connect("localhost:2181");
    // System.out.println("/zk是否存在:" + client.isExisted("/zk", true));
    String path = client.createZnode("/zk/lock_", "节点", null, CreateMode.EPHEMERAL_SEQUENTIAL);
    System.out.println("创建的节点的路径为:" + path);
    System.out.println(client.getChildren("/zk", false));
    // System.out.println("/zk是否存在:" + client.isExisted("/zk", true));
    // client.deleteZnode("/zk");
  }

}
