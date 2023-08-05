package com.universe.thirdparty.easyrule.helloworld;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

/**
 * 声明式规则定义
 * @author Nick Liu
 * @date 2023/8/3
 */
@Rule(name = "Hello world rule", description = "Always say hello world")
public class DeclarativeHelloWorldRule {

	@Condition
	public boolean when(@Fact("enabled") boolean enabled) {
		return enabled;
	}

	@Action(order = 1)
	public void then(@Fact("enabled") boolean enabled) throws Exception {
		System.out.println("Hello World");
	}

	@Action(order = 2)
	public void finalAction(Facts facts) throws Exception {
		System.out.println("Final Hello World");
	}

	public static void main(String[] args) {
		Facts facts = new Facts();
		facts.put("enabled", true);

		Rules rules = new Rules();
		rules.register(new DeclarativeHelloWorldRule());

		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.fire(rules, facts);
	}
}
