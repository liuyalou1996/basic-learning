package com.universe.thirdparty.easyrule.helloworld;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

/**
 * @author Nick Liu
 * @date 2023/8/3
 */
public class ProgrammaticHelloWorldRule implements Rule {

	@Override
	public boolean evaluate(Facts facts) {
		return Boolean.parseBoolean(facts.get("enabled"));
	}

	@Override
	public void execute(Facts facts) throws Exception {
		System.out.println("Hello World");
	}

	@Override
	public int compareTo(Rule o) {
		return 0;
	}
}
