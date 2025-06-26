package org.copyria2.order_service.mapper;

import org.copyria2.order_service.client.rest.model.ResponseOrderViewsDto;
import org.copyria2.order_service.entity.OrderView;
import org.mapstruct.Mapper;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface OrderViewMapper {
    ResponseOrderViewsDto toDto(OrderView entity);
}
