package com.universe.thirdparty.easyrule.fizzbuzz;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;

/**
 * @author Nick Liu
 * @date 2023/8/4
 */
public class FizzBuzzRulesLauncher {

	public static void main(String[] args) {
		// 如果第一条规则满足条件且行为执行成功后则跳过执行后面的规则
		RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
		RulesEngine rulesEngine = new DefaultRulesEngine(parameters);

		// 规则注册
		Rules rules = new Rules();
		rules.register(new FizzRule());
		rules.register(new BuzzRule());
		rules.register(new FizzBuzzRule(new FizzRule(), new BuzzRule()));
		rules.register(new NonFizzBuzzRule());

		// 触发规则
		Facts facts = new Facts();
		for (int count = 0; count < 100; count++) {
			facts.put("number", count);
			rulesEngine.fire(rules, facts);
		}
	}
}
