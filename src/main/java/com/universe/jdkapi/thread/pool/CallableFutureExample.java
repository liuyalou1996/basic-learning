package com.universe.jdkapi.thread.pool;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

/**
 * Future：代表异步计算的结果，提供的方法用来检查计算是否完成，完成则可通过get方法检索计算结果，否则get方法将会阻塞。通过cancel方法可取消，一旦任务完成，则不能取消。<br/>
 * CompletionService:分离异步任务的生产和已完成任务的消费，消费者可取出已完成的任务，并且根据完成的顺序处理结果。<br/>
 * FutureTask:可取消的异步计算，这个类实现了RunnableFuture接口，RunnableFuture继承了Runnable和Future接口。<br/>
 * ExecutorService:实现了Executor接口，代表一个执行器，提供了一些方法管理任务的终止以及生成Future跟踪一个或多个任务的进度。<br/>
 */
public class CallableFutureExample {

	public static void simpleUsage() throws Exception {
		Callable<Integer> callable = () -> new Random().nextInt(1000);
		FutureTask<Integer> task = new FutureTask<>(callable);
		new Thread(task).start();
		System.out.println(task.get());
	}

	public static void combineWithExecutorService() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Integer> future = executor.submit(() -> new Random().nextInt(1000));
		System.out.println(future.get());
	}

	public static void combineWithCompletionService() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<Integer> service = new ExecutorCompletionService<>(executor);
		for (int count = 0; count < 10; count++) {
			int taskId = count + 1;
			service.submit(() -> {
				int randomTimeout = new Random().nextInt(10);
				LockSupport.parkNanos(randomTimeout * 1000 * 1000 * 1000L);
				return taskId;
			});
		}
		for (int count = 0; count < 10; count++) {
			// 根据完成的顺序取出结果
			System.out.println("执行结果为：" + service.take().get());
		}
		executor.shutdown();
	}

	public static void main(String[] args) throws Exception {
		combineWithCompletionService();
	}
}
