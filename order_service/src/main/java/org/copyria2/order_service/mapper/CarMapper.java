package org.copyria2.order_service.mapper;

import org.copyria2.order_service.client.rest.model.CarResponseDto;
import org.copyria2.order_service.client.rest.model.CreateCarDto;
import org.copyria2.order_service.client.rest.model.CreateOrderCarDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderCarDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CarMapper {
    CreateCarDto ToCreateCarDto(CreateOrderCarDto carDto, Integer orderId);
    ResponseOrderCarDto ToOrderCarResponseDto(CreateOrderCarDto carDto);
    ResponseOrderCarDto ToOrderCarResponseDto(CarResponseDto carDto);

}
