package com.universe.jdkapi.jdk8.async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

/**
 * @author Nick Liu
 * @date 2025/9/13
 */
public class CompletableFutureJoinExample {

	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		List<CompletableFuture<Boolean>> futures = IntStream.range(0, 5).mapToObj(count -> CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName() + "开始运行");
			LockSupport.parkNanos(1000L * 1000 * 1000 * count);
			System.out.println(Thread.currentThread().getName() + "结束运行");
			return true;
		}, executor)).toList();

		CompletableFuture<Void> completableFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[] {}));
		completableFuture.join();
		futures.forEach(CompletableFutureJoinExample::accept);

		executor.shutdown();
	}

	private static void accept(CompletableFuture<Boolean> future) {
		try {
			// 也可使用future.get()方法
			System.out.println("运行结果:" + future.join());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
