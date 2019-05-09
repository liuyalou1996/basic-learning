package com.universe.middleware.zk.curator.framework;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class TransactionExample {

  public static void main(String[] args) {
    ExponentialBackoffRetry policy = new ExponentialBackoffRetry(1000, 3);

    try (CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", policy)) {
      client.start();

      CuratorOp create = client.transactionOp().create().forPath("/transaction", "trans".getBytes());
      CuratorOp setData = client.transactionOp().setData().forPath("/another/path", "other".getBytes());
      CuratorOp delete = client.transactionOp().delete().forPath("/yet/another/path");
      // 事务中的操作要么全做，要么全不做，确保原子性
      List<CuratorTransactionResult> resultList = client.transaction().forOperations(create, setData, delete);

      for (CuratorTransactionResult result : resultList) {
        System.out.println(result.getForPath() + " - " + result.getType());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
