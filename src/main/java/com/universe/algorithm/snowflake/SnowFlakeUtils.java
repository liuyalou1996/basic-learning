package com.universe.algorithm.snowflake;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author 刘亚楼
 * @date 2022/4/6
 */
public abstract class SnowFlakeUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(SnowFlakeUtils.class);

	/**
	 * 起始的时间戳
	 */
	private final static long START_STMP = 1480166465631L;

	/**
	 * 每一部分占用的位数
	 */
	private final static long SEQUENCE_BIT = 12; //序列号占用的位数
	private final static long MACHINE_BIT = 5;   //机器标识占用的位数
	private final static long DATACENTER_BIT = 5;//数据中心占用的位数

	/**
	 * 每一部分的最大值
	 */
	private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
	private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
	private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

	/**
	 * 每一部分向左的位移
	 */
	private final static long MACHINE_LEFT = SEQUENCE_BIT;
	private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
	private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

	private static long datacenterId;  //数据中心
	private static long machineId;     //机器标识
	private static long sequence = 0L; //序列号
	private static long lastStmp = -1L;//上一次时间戳

	static {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostName = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			datacenterId = Arrays.stream(StringUtils.toCodePoints(hostName)).sum();
			machineId = Arrays.stream(StringUtils.toCodePoints(hostAddress)).sum();
		} catch (UnknownHostException e) {
			LOGGER.error("Fail to generate snowflake id:{}", e.getMessage());
		}
	}

	/**
	 * 产生下一个ID
	 *
	 * @return
	 */
	public static synchronized long nextId() {
		long currStmp = getNewstmp();
		if (currStmp < lastStmp) {
			throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
		}

		if (currStmp == lastStmp) {
			//相同毫秒内，序列号自增
			sequence = (sequence + 1) & MAX_SEQUENCE;
			//同一毫秒的序列数已经达到最大
			if (sequence == 0L) {
				currStmp = getNextMill();
			}
		} else {
			//不同毫秒内，序列号置为0
			sequence = 0L;
		}

		lastStmp = currStmp;

		return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
			| datacenterId << DATACENTER_LEFT       //数据中心部分
			| machineId << MACHINE_LEFT             //机器标识部分
			| sequence;                             //序列号部分
	}

	private static long getNextMill() {
		long mill = getNewstmp();
		while (mill <= lastStmp) {
			mill = getNewstmp();
		}
		return mill;
	}

	private static long getNewstmp() {
		return System.currentTimeMillis();
	}

}
