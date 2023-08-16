package com.universe.jdkapi.jdk8.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 刘亚楼
 * @date 2021/6/10
 */
public class CompletableFutureExample {

	public static void main(String[] args) throws Exception {
		multiTasksRunningAsOrder();
	}

	private static void simpleAsyncTaskRunning() throws InterruptedException {
		// 异步方法执行如果没有显示指定Executor参数，则默认使用ForkJoin.commmonPool()
		// 使用ForkJoin.commonPool时，如果虚拟机一退出，那么对应使用该线程池执行的异步任务也会终止
		CompletableFuture.supplyAsync(CompletableFutureExample::calPrice)
			.thenAccept(System.out::println)
			.exceptionally(e -> {
					e.printStackTrace();
					return null;
				}
			);
		TimeUnit.SECONDS.sleep(1);
	}

	private static void multiTasksRunningAsOrder() throws InterruptedException {
		CompletableFuture.supplyAsync(() -> queryStockCode("北京生物"))
			.thenApplyAsync(CompletableFutureExample::calPrice)
			.thenAccept(System.out::println)
			.exceptionally(e -> {
				e.printStackTrace();
				return null;
			});
		TimeUnit.SECONDS.sleep(1);
	}

	private static String queryStockCode(String stockName) {
		LockSupport.parkNanos(1000 * 1000 * 100L);
		return "10000";
	}

	private static Double calPrice(String code) {
		System.out.println("code: " + code);
		return calPrice();
	}

	private static Double calPrice() {
		LockSupport.parkNanos(1000 * 1000 * 500L);
		if (Math.random() < 0.5) {
			throw new RuntimeException("Price Calculation failed");
		}
		return Math.random();
	}
}
