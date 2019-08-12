/*
 * Copyright (C) 2011-2019 ShenZhen iBOXCHAIN Information Technology Co.,Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary
 * information of iBoxChain Company of China.
 * ("Confidential Information"). You shall not disclose
 * such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement
 * you entered into with iBoxchain inc.
 */
package com.universe.algorithm.snowflake;

/**
 * 分布式id生成器，采用snowflake算法
 * @author: liuyalou
 * @date: 2019年7月25日
 */
public class IdGenerator {

  /** 开始时间截 (2015-01-01) */
  private final long startTimeMillis = 1420041600000L;

  /** 机器id所占的位数 */
  private final long workerIdBits = 5L;

  /** 数据标识id所占的位数 */
  private final long datacenterIdBits = 5L;

  /** 支持的最大机器id，结果是31 */
  private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

  /** 支持的最大数据标识id，结果是31 */
  private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

  /** 序列在id中占的位数 */
  private final long sequenceBits = 12L;

  /** 机器ID向左移12位 */
  private final long workerIdShift = sequenceBits;

  /** 数据标识id向左移17位(12+5) */
  private final long datacenterIdShift = sequenceBits + workerIdBits;

  /** 时间截向左移22位(5+5+12) */
  private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

  /** 生成序列的掩码，这里为4095 */
  private final long sequenceMask = -1L ^ (-1L << sequenceBits);

  /** 工作机器ID(0~31) */
  private long workerId;

  /** 数据中心ID(0~31) */
  private long datacenterId;

  /** 毫秒内序列(0~4095) */
  private long sequence = 0L;

  /** 上次生成ID的时间截 */
  private long lastTimestamp = -1L;

  /**
   * 构造函数
   * @param workerId 工作ID (0~31)
   * @param datacenterId 数据中心ID (0~31)
   */
  public IdGenerator(long workerId, long datacenterId) {
    if (workerId > maxWorkerId || workerId < 0) {
      throw new IllegalArgumentException(
          String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
    }

    if (datacenterId > maxDatacenterId || datacenterId < 0) {
      throw new IllegalArgumentException(
          String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
    }

    this.workerId = workerId;
    this.datacenterId = datacenterId;
  }

  /**
   * 获得下一个ID (该方法是线程安全的)
   * @return SnowflakeId
   */
  public synchronized long nextId() {
    long timestamp = getCurrentTimeMillis();

    // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
    if (timestamp < lastTimestamp) {
      throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
          lastTimestamp - timestamp));
    }

    // 如果是同一时间生成的，则进行毫秒内序列自增
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;
      // 毫秒内序列溢出
      if (sequence == 0) {
        // 阻塞到下一个毫秒,获得新的时间戳
        timestamp = getNextTimeMillis(lastTimestamp);
      }
    } else {
      sequence = 0L;
    }

    // 上次生成ID的时间截
    lastTimestamp = timestamp;

    // 移位并通过或运算拼到一起组成64位的ID
    return ((timestamp - startTimeMillis) << timestampLeftShift) // 时间戳左移22位
        | (datacenterId << datacenterIdShift) // datacenterId左移12+5位
        | (workerId << workerIdShift) // workerId左移12位
        | sequence;
  }

  /**
   * 阻塞到下一个毫秒，直到获得新的时间戳
   * @param lastTimestamp 上次生成ID的时间截
   * @return 当前时间戳
   */
  protected long getNextTimeMillis(long lastTimestamp) {
    long timestamp = getCurrentTimeMillis();
    while (timestamp <= lastTimestamp) {
      timestamp = getCurrentTimeMillis();
    }

    return timestamp;
  }

  /**
   * 返回以毫秒为单位的当前时间
   * @return 当前时间(毫秒)
   */
  protected long getCurrentTimeMillis() {
    return System.currentTimeMillis();
  }

  public static void main(String[] args) {
    IdGenerator generator = new IdGenerator(0, 0);
    System.out.println(String.valueOf(generator.nextId()));
  }
}
