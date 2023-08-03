package com.universe.thirdparty.easyrule.fizzbuzz;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;

/**
 * 被7整除的规则
 * @author Nick Liu
 * @date 2023/8/4
 */
@Rule
public class BuzzRule {

	@Condition
	public boolean isBuzz(@Fact("number") Integer number) {
		return number % 7 == 0;
	}

	@Action
	public void printBuzz(@Fact("number") Integer number) throws Exception {
		System.out.println("能被7整除的数为: " + number);
	}

	@Priority
	public int getPriority() {
		return 2;
	}
}
