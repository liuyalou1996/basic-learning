package com.universe.thirdparty.easyrule.fizzbuzz;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;

/**
 * 既不能被5整除也不能被7整除的规则
 * @author Nick Liu
 * @date 2023/8/4
 */
@Rule
public class NonFizzBuzzRule {

	@Condition
	public boolean isNotFizzOrBuzz(@Fact("number") Integer number) {
		return number % 5 != 0 || number % 7 != 0;
	}

	@Action
	public void printInput(@Fact("number") Integer number) throws Exception {
		System.out.println("既不能被5整除也不能被7整除的数字: " + number);
	}

	@Priority
	public int getPriority() {
		return 3;
	}

}
