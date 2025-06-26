package org.copyria2.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderDto;

import org.copyria2.order_service.client.rest.model.UpdateOrderDto;
import org.copyria2.order_service.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.copyria2.order_service.client.rest.controller.OrdersApi;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {
    private final OrderService orderService;
    @Override
    public ResponseEntity<Void> deleteOrder(Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ResponseOrderDto> getOrder(Integer id, String curr) {
        return ResponseEntity.ok(orderService.getOrder(id, curr));
    }

    @Override
    public ResponseEntity<List<ResponseOrderDto>> getOrders(String city, String region, BigDecimal minPrice, BigDecimal maxPrice, String currency) {
        return ResponseEntity.ok(orderService.getOrders(city,region,minPrice,maxPrice,currency));
    }

    @Override
    public ResponseEntity<ResponseOrderDto> postOrder(CreateOrderDto createOrderDto) {
        return ResponseEntity.ok(orderService.createOrder(createOrderDto));
    }

    @Override
    public ResponseEntity<ResponseOrderDto> updateOrder(Integer id, UpdateOrderDto updateOrderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id,updateOrderDto));
    }

}
