package org.copyria2.order_service.mapper;

import org.copyria2.order_service.client.rest.model.*;
import org.copyria2.order_service.client.rest.model.CreateOrderCarDto;
import org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseChangeDto;
import org.copyria2.order_service.client.rest.model.ResponseObjDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderCarDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderViewsDto;
import org.copyria2.order_service.client.rest.model.UpdateOrderDto;
import org.copyria2.order_service.entity.OrderEntity;
import org.copyria2.order_service.entity.OrderView;
import org.mapstruct.*;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING)
public interface OrderMapper {
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "editedTimes", constant = "1")
    OrderEntity ToEntity(CreateOrderDto order);
    @Mapping(target = "car", source = "carDto")
    @Mapping(target = "views", source = "viewsDto")
    ResponseOrderDto ToResponseOrderDto(OrderEntity order, ResponseOrderCarDto  carDto, ResponseOrderViewsDto viewsDto);
    @Mapping(target = "orders", source = "orders")
    @Mapping(target = "changes", source = "changes")
    ResponseObjDto toResponseObj(List<ResponseOrderDto> orders, List<ResponseChangeDto> changes);
    @Mapping(target ="views")
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    OrderEntity updateOrderEntityViews(@MappingTarget OrderEntity order, OrderView views);
    @Mapping(ignore = true, target = "id")
    @Mapping(target="status", constant = "UPDATED")
    @Mapping(target = "editedTimes", ignore = true)
    @Mapping(ignore = true, target ="avgPrice")
    @Mapping(ignore = true, target ="avgPriceByRegion")
    @Mapping(ignore = true, target ="views")
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    OrderEntity updateOrderEntity(@MappingTarget OrderEntity order, UpdateOrderDto updateOrderDto);
    @AfterMapping
    default void incrementEditedTimes(@MappingTarget OrderEntity order) {
        int current = order.getEditedTimes();
        order.setEditedTimes(current + 1);
    }
}
