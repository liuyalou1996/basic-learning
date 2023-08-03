package com.universe.thirdparty.easyrule.fizzbuzz;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;

/**
 * 被5整除的规则
 * @author Nick Liu
 * @date 2023/8/4
 */
@Rule
public class FizzRule {

	@Condition
	public boolean isFizz(@Fact("number") Integer number) {
		return number % 5 == 0;
	}

	@Action
	public void printFizz(@Fact("number") Integer number) throws Exception {
		System.out.println("能被5整除的数为: " + number);
	}

	@Priority
	public int getPriority() {
		return 1;
	}

}
