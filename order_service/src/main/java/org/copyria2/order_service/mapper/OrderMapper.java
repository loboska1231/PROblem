package org.copyria2.order_service.mapper;

import org.copyria2.order_service.client.rest.model.*;
import org.copyria2.order_service.client.rest.model.CreateOrderCarDto;
import org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseChangeDto;
import org.copyria2.order_service.client.rest.model.ResponseObjDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderCarDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderDto;
import org.copyria2.order_service.client.rest.model.UpdateOrderDto;
import org.copyria2.order_service.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
@Mapper(componentModel = SPRING)
public interface OrderMapper {
    @Mapping(target = "status", constant = "CREATE")
    @Mapping(target = "editedTimes", constant = "1")
    OrderEntity ToEntity(CreateOrderDto order);
    @Mapping(target = "car", source = "carDto")
    ResponseOrderDto ToResponseOrderDto(OrderEntity order,ResponseOrderCarDto  carDto);
//    @Mapping(ignore = true, target = "id")
//    @Mapping(target="status", constant = "UPDATED")
//    @Mapping(target = "editedTimes", ignore = true)
//    @Mapping(ignore = true, target ="avgPrice")
//    @Mapping(ignore = true, target ="avgPriceByRegion")
//    @Mapping(ignore = true, target ="totalViews")
//    @Mapping(ignore = true, target ="viewsPerDay")
//    @Mapping(ignore = true, target ="viewsPerWeek")
//    @Mapping(ignore = true, target ="viewsPerMonth")
    default OrderEntity updateOrderEntity( OrderEntity order, UpdateOrderDto updateOrderDto){
      order.setCity(updateOrderDto.getCity());
      order.setRegion(updateOrderDto.getRegion());
      order.setOwnerEmail(updateOrderDto.getOwnerEmail());
      order.setPrice(updateOrderDto.getPrice());
      order.setCurrency(updateOrderDto.getCurrency());
      int t =order.getEditedTimes() +1;
      order.setEditedTimes(t);
      return order;
    }
    @Mapping(target = "orders", source = "orders")
    @Mapping(target = "changes", source = "changes")
    ResponseObjDto toResponseObj(List<ResponseOrderDto> orders, List<ResponseChangeDto> changes);
}
