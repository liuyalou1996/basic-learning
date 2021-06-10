package com.universe.thirdparty.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * @author 刘亚楼
 * @date 2021/6/10
 */
public class ReactorOpeatorExample {

	private static List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");

	public static void main(String[] args) {
		firstEmitting();
	}

	private static void simpleCreation() {
		Flux<String> fewWords = Flux.just("Hello", "World");
		Flux<String> manyWords = Flux.fromIterable(words);

		fewWords.subscribe(System.out::println);
		System.out.println();
		manyWords.subscribe(System.out::println);
	}

	private static void findMissingLetter() {
		Flux<String> letters = Flux.fromIterable(words)
			.flatMap(word -> Flux.fromArray(word.split("")))
			.distinct()
			.sort()
			.zipWith(Flux.range(1, Integer.MAX_VALUE), (letter, count) -> String.format("%d.%s", count, letter));
		letters.subscribe(System.out::println);
	}

	private static void restoreMissingLetter(){
		Flux<String> letters = Flux.fromIterable(words)
			.flatMap(word -> Flux.fromArray(word.split("")))
			.concatWith(Mono.just("s"))
			.distinct()
			.sort()
			.zipWith(Flux.range(1, Integer.MAX_VALUE), (word, count) -> String.format("%d.%s", count, word));
		letters.subscribe(System.out::println);
	}

	private static void shortCircuit() {
		Flux<String> words = Mono.just("Hello").concatWith(Mono.just("World")).delaySubscription(Duration.ofMillis(500));
		// 这里不会看到打印内容，因为subscription有延迟
		words.subscribe(System.out::println);
	}

	private static void block() {
		Flux<String> words = Mono.just("Hello").concatWith(Mono.just("World")).delaySubscription(Duration.ofMillis(1000));
		// 使用toIterable或者toStream方法转换为阻塞模式
		words.toStream().forEach(System.out::println);
	}

	private static void firstEmitting(){
		Mono<String> a = Mono.just("oops I'm late").delaySubscription(Duration.ofMillis(450));
		Flux<String> b = Flux.just("let's get", "the party", "started").delaySubscription(Duration.ofMillis(400));
		// 选出第一个Publisher实例
		Flux.firstWithSignal(a, b).toIterable().forEach(System.out::println);
	}


}
