package com.universe.thirdparty.easyrule.fizzbuzz;

import org.jeasy.rules.support.composite.UnitRuleGroup;

import java.util.Arrays;

/**
 * 既能被5整除也能被7整除的规则
 * @author Nick Liu
 * @date 2023/8/4
 */
public class FizzBuzzRule extends UnitRuleGroup {

	public FizzBuzzRule(Object... rules) {
		Arrays.stream(rules).forEach(super::addRule);
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
