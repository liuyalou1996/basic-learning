package com.universe.thirdparty.easyrule.helloworld;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

/**
 * 编程式规则定义
 * @author Nick Liu
 * @date 2023/8/3
 */
public class ProgrammaticHelloWorldRule implements Rule {

	@Override
	public boolean evaluate(Facts facts) {
		return facts.get("enabled");
	}

	@Override
	public void execute(Facts facts) throws Exception {
		System.out.println("Hello World");
	}

	@Override
	public int compareTo(Rule o) {
		return 0;
	}

	public static void main(String[] args) {
		Facts facts = new Facts();
		facts.put("enabled", true);

		Rules rules = new Rules();
		rules.register(new ProgrammaticHelloWorldRule());

		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.fire(rules, facts);
	}
}
