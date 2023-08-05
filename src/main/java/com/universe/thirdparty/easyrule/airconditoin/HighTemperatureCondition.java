package com.universe.thirdparty.easyrule.airconditoin;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

/**
 * @author Nick Liu
 * @date 2023/8/5
 */
public class HighTemperatureCondition implements Condition {

	@Override
	public boolean evaluate(Facts facts) {
		int temperature = facts.get("temperature");
		return temperature > 25;
	}
}
