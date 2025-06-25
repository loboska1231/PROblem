package org.copyria2.car_service.mapper;

import org.copyria2.car_service.entity.CarEntity;
import org.copyria2.carservice.api.rest.model.CreateCarDto;
import org.copyria2.carservice.api.rest.model.CarResponseDto;

import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CarMapper {
    CarResponseDto toResponseDto(CarEntity car);
    CarEntity toEntity(CreateCarDto car);
}
