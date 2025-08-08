package com.universe.jdkapi.jdk21.virtualThreads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.LockSupport;

/**
 * 虚拟线程是Java为解决传统线程数量瓶颈而引入的新的线程模型，虚拟线程由JVM管理，而非操作系统线程。
 * 因此操作成本低，非常适合Web服务、高并发I/O场景。
 *
 * @author Nick Liu
 * @date 2025/8/8
 */
public class VirtualThreadsExample {

	public static void main(String[] args) {
		Thread.startVirtualThread(() -> {
			System.out.println(STR."这是一个虚拟线程：\{Thread.currentThread()}");
		});

		ThreadFactory factory = Thread.ofVirtual().factory();
		Runnable task = () -> System.out.println(STR."任务执行：\{Thread.currentThread()}");
		factory.newThread(task).start();

		LockSupport.parkNanos(2 * 1000 * 1000 * 1000L);
	}
}
