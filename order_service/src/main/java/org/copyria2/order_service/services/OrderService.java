package org.copyria2.order_service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.copyria2.order_service.client.rest.api.CarApi;
import org.copyria2.order_service.client.rest.model.*;
import org.copyria2.order_service.client.rest.model.ResponseChangeDto;
import org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseObjDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderCarDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderDto;
import org.copyria2.order_service.client.rest.model.UpdateOrderDto;
import org.copyria2.order_service.entity.OrderEntity;
import org.copyria2.order_service.mapper.CarMapper;
import org.copyria2.order_service.mapper.ChangeMapper;
import org.copyria2.order_service.mapper.OrderMapper;
import org.copyria2.order_service.repository.ChangeRepository;
import org.copyria2.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ChangeRepository changeRepository;
    private final OrderMapper orderMapper;
    private final ChangeMapper changeMapper;
    private final CarMapper carMapper;
    @Qualifier("serviceAuthCarApi")
    private final CarApi carApi;
    public void deleteOrder(Integer id) {
        var car = carApi.getCarByOrderId(id);
        carApi.deleteCarById(car.getId());
        orderRepository.deleteById(id);
    }
    public ResponseOrderDto getOrder(Integer id, String curr){
        OrderEntity order = orderRepository.findById(id).get();
        var car = carApi.getCarByOrderId(id);
        return orderMapper.ToResponseOrderDto(order, carMapper.ToOrderCarResponseDto(car));
    }

    public ResponseOrderDto createOrder(CreateOrderDto dto){
        if(dto.getCurrency() !="USD" || dto.getCurrency() !="EUR" || dto.getCurrency() !="UAH"){
            return new ResponseOrderDto() ;
        }
        var save = orderRepository.save(orderMapper.ToEntity(dto));
        var car = dto.getCar();
        var carDto = carMapper.ToCreateCarDto(car, save.getId());
        carApi.createCar(carDto);
        return orderMapper.ToResponseOrderDto(save, carMapper.ToOrderCarResponseDto(car));
    }
    public ResponseObjDto getOrders(String city, String region, BigDecimal minPrice, BigDecimal maxPrice, String currency){
        List<OrderEntity> orders;
        if(
                city != null && region != null
                        && minPrice != null && maxPrice != null
        ) {
            orders = orderRepository.findAllByCityAndPriceGreaterThanEqualAndPriceLessThanEqual(city, minPrice, maxPrice);
        } else if(
                region != null && minPrice != null && maxPrice != null
        ){
            orders = orderRepository.findAllByRegionAndPriceGreaterThanEqualAndPriceLessThanEqual(region, minPrice, maxPrice);
        } else if (minPrice != null && maxPrice != null) {
            orders = orderRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice);
        } else if (minPrice != null ) {
            orders = orderRepository.findAllByPriceGreaterThanEqual(minPrice);
        } else if(maxPrice != null) {
            orders = orderRepository.findAllByPriceLessThanEqual(maxPrice);
        } else {
            orders = orderRepository.findAll();
        }
        orders = orders.stream().map(order->{
            if(currency!= null && !order.getCurrency().equals(currency)){
                String curr = order.getCurrency();
                BigDecimal fromRate = curr.equals("UAH") ? BigDecimal.valueOf(1.0): changeRepository.findByCurrency(curr).getBuy();
                BigDecimal toRate = currency.equals("UAH") ? BigDecimal.valueOf(1.0) : changeRepository.findByCurrency(currency).getBuy();
                BigDecimal converted = order.getPrice().multiply(fromRate).divide(toRate, RoundingMode.HALF_UP);
                order.setPrice(converted);
                order.setCurrency(currency);
            }
            return order;
        }).toList();
        List<ResponseOrderDto> ordersDtos = orders.stream().map(order -> {
            var car = carApi.getCarByOrderId(order.getId());
            ResponseOrderCarDto carDto = carMapper.ToOrderCarResponseDto(car);
            return orderMapper.ToResponseOrderDto(order, carDto);
        }).toList();
        List<ResponseChangeDto> changes = changeRepository.findAll().stream().map(changeMapper::toChangeDto).toList();
        return orderMapper.toResponseObj(ordersDtos, changes);
    }
    @Transactional
    public ResponseOrderDto updateOrder(Integer id, UpdateOrderDto dto){
        return orderRepository.findById(id)
                .map(order -> {
                    orderMapper.updateOrderEntity(order, dto);
                    var car = carApi.getCarByOrderId(order.getId());
                    return orderMapper.ToResponseOrderDto(order,carMapper.ToOrderCarResponseDto(car));
                }).get();
    }

    public void updateOrderStatus(Integer id,String status) {
        orderRepository.findById(id).ifPresent(order -> {
            if(order.getEditedTimes() !=3){
                order.setStatus(status);
            }
            else{
                order.setStatus("NonActive");
                // mail
            }
        });
    }
    public OrderEntity getOrderByCarId(Integer carId){
        Integer orderId = carApi.getCarById(carId).getOrderId();
        return orderRepository.findById(orderId).get();
    }
}
