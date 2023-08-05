package com.universe.thirdparty.easyrule.airconditoin;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

/**
 * @author Nick Liu
 * @date 2023/8/5
 */
public class DecreaseTemperatureAction implements Action {

	@Override
	public void execute(Facts facts) throws Exception {
		int temperature = facts.get("temperature");
		System.out.printf("Current temperature: %d, It's hot! cooling air...%n", temperature);
		facts.put("temperature", temperature - 1);
	}
}
