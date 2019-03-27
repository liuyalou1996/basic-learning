package com.iboxpay.middleware.zk.curator.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CreateClientExample {

  private static final String CONNECTION_STRING = "localhost:2181";

  /**
   * 指数型递减重试策略，第一个参数为睡眠时间，第二个参数为重试次数
   */
  private static final ExponentialBackoffRetry RETRY_POLICY = new ExponentialBackoffRetry(1000, 3);

  /**
   * 工厂方法模式提供了简便的模式来创建客户端实例
   * @return
   */
  public static CuratorFramework createSimpleClient() {
    return CuratorFrameworkFactory.newClient(CONNECTION_STRING, RETRY_POLICY);
  }

  /**
   * 建造者模式对创建客户端实例有更细粒的控制
   * @return
   */
  public static CuratorFramework createClientWithDetailedParams() {
    return CuratorFrameworkFactory.builder().connectString(CONNECTION_STRING).retryPolicy(RETRY_POLICY)
        .connectionTimeoutMs(15 * 1000).sessionTimeoutMs(60 * 1000).build();
  }

}
