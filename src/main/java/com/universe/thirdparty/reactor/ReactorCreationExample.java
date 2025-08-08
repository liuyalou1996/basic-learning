package com.universe.thirdparty.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.*;

import java.util.Arrays;

/**
 * @author Nick Liu
 * @date 2025/8/8
 */
public class ReactorCreationExample {

	public static void main(String[] args) {
		baseSubscriber();
	}

	public static void simpleCreation() {
		Mono<String> emptyMono = Mono.empty();
		emptyMono.subscribe(System.out::println);

		System.out.println("Mono单个元素遍历：");
		Mono<String> simpleEleMono = Mono.just("foo");
		simpleEleMono.subscribe(System.out::println);

		System.out.println("Flux数值遍历：");
		Flux<Integer> numberFlux = Flux.range(1, 10);
		numberFlux.subscribe(System.out::println);

		System.out.println("Flux字符串遍历：");
		Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("foo", "bar", "foobar"));
		stringFlux.subscribe(System.out::println);
	}

	public static void multipleSubscribe() {
		Flux.range(1, 4).handle((v, sink) -> {
			if (v <= 3) {
				sink.next(v);
				return;
			}
			sink.error(new RuntimeException("Go to 4"));
		}).subscribe(System.out::println, // 第一个参数为元素Consumer
			error -> System.err.println("Error: " + error), // 第二个参数为错误Consumer
			() -> System.out.println("Done")); // 第三个参数为Complete Consumer
	}

	public static void baseSubscriber() {
		Flux.range(1, 10)
			.doOnRequest(v -> System.out.println("request of " + v))
			.subscribe(new BaseSubscriber<Integer>() {

				@Override
				protected void hookOnSubscribe(Subscription subscription) {
					super.request(1);
				}

				@Override
				protected void hookOnNext(Integer value) {
					System.out.println("Cancelling after having received " + value);
					// 如果不调用request方法后续将不会获取到新元素
					cancel();
				}
			});
	}


}
