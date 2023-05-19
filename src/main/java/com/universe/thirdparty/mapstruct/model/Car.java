package com.universe.thirdparty.mapstruct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nick Liu
 * @date 2023/5/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

	private String make;
	private int numberOfSeats;
	private CardType type;
}
