package org.copyria2.order_service.mapper;

import org.copyria2.order_service.entity.OrderEntity;
import  org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
@Mapper(componentModel = SPRING)
public interface OrderMapper {
    @Mapping(target = "status", constant = "CREATE")
    OrderEntity ToEntity(CreateOrderDto order);

}
