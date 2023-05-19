package com.universe.thirdparty.mapstruct;

import com.universe.thirdparty.mapstruct.converter.CarConverter;
import com.universe.thirdparty.mapstruct.model.Car;
import com.universe.thirdparty.mapstruct.model.CardDTO;
import com.universe.thirdparty.mapstruct.model.CardType;

/**
 * @author Nick Liu
 * @date 2023/5/19
 */
public class CardConverterTest {

	public static void main(String[] args) {
		Car car = new Car("Morris", 5, CardType.BENZ);
		CardDTO cardDTO = CarConverter.INSTANCE.carToCardDTO(car);
		System.out.println(cardDTO);
	}
}
