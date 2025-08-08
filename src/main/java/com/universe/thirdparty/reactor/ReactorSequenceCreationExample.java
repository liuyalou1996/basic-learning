package com.universe.thirdparty.reactor;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Nick Liu
 * @date 2025/8/8
 */
public class ReactorSequenceCreationExample {

	public static void main(String[] args) {
		mutableStateVariant();
	}

	public static void stateBasedGeneration() {
		Flux.generate(() -> 0, (state, sink) -> {
			sink.next("3 x " + state + " = " + 3 * state);
			if (state == 10) {
				sink.complete();
			}
			return ++state;
		}).subscribe(System.out::println);
	}

	public static void mutableStateVariant() {
		Flux<String> flux = Flux.generate(AtomicLong::new, (state, sink) -> {
			long i = state.getAndIncrement();
			sink.next("3 x " + i + " = " + 3 * i);
			if (i == 10) {
				sink.complete();
			}
			return state;
		}, (state) -> System.out.println("state: " + state));
		flux.subscribe(System.out::println);
	}
}
