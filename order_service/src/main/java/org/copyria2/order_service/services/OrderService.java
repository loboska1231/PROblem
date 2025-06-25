package org.copyria2.order_service.services;

import lombok.RequiredArgsConstructor;
import org.copyria2.order_service.client.rest.api.CarApi;
import org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderCarDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderDto;
import org.copyria2.order_service.client.rest.model.UpdateOrderDto;
import org.copyria2.order_service.entity.OrderEntity;
import org.copyria2.order_service.mapper.OrderMapper;
import org.copyria2.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Qualifier("serviceAuthCarApi")
    private final CarApi carApi;
    public void deleteOrder(Integer id) {
        Integer carId= orderRepository.findById(id).get().getCarId();
        carApi.deleteCarById(carId);
        orderRepository.deleteById(id);
    }
//    public ResponseOrderDto getOrder(Integer id, String curr){
//        OrderEntity orderEntity = orderRepository.findById(id).get();
//        ResponseOrderCarDto carDto = carApi.getCarById(orderEntity.getId());
//    }

//    public ResponseOrderDto createOrder(CreateOrderDto dto){
//        OrderEntity order = orderMapper.ToEntity(dto);
//    }

//    public void updateOrder(Integer id, UpdateOrderDto dto){
//        return orderRepository.findById(id)
//                .map(order ->orderMapper.)
//                ;
//    }
}
