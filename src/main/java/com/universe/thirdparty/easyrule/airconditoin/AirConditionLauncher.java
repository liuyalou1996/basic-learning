package com.universe.thirdparty.easyrule.airconditoin;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.InferenceRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

/**
 * @author Nick Liu
 * @date 2023/8/5
 */
public class AirConditionLauncher {

	public static void main(String[] args) {
		Facts facts = new Facts();
		facts.put("temperature", 30);

		// 通过规则构建API定义规则
		Rule rule = new RuleBuilder()
			.name("Air Condition Rule")
			.when(new HighTemperatureCondition())
			.then(new DecreaseTemperatureAction())
			.build();
		Rules rules = new Rules();
		rules.register(rule);

		// 基于事实重复应用规则引擎，直到规则不再满足
		RulesEngine rulesEngine = new InferenceRulesEngine();
		rulesEngine.fire(rules, facts);
	}
}
