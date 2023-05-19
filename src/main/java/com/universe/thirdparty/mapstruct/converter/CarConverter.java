package com.universe.thirdparty.mapstruct.converter;

import com.universe.thirdparty.mapstruct.model.Car;
import com.universe.thirdparty.mapstruct.model.CardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Nick Liu
 * @date 2023/5/19
 */
@Mapper
public interface CarConverter {

	CarConverter INSTANCE = Mappers.getMapper(CarConverter.class);

	@Mapping(source = "numberOfSeats", target = "seatCount")
	CardDTO carToCardDTO(Car car);
}
