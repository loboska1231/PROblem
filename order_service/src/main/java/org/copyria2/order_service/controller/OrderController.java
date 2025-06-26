package org.copyria2.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseObjDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderDto;

import org.copyria2.order_service.client.rest.model.UpdateOrderDto;

import org.copyria2.order_service.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.copyria2.order_service.client.rest.controller.OrdersApi;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {
    private final OrderService orderService; // работает но странно
    @PreAuthorize("hasRole('CAR_SELLER')")
    @Override
    public ResponseEntity<Void> deleteOrder(Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ResponseOrderDto> getOrder(Integer id, String currency) {
        return ResponseEntity.ok(orderService.getOrder(id, currency));
    }

    @Override
    public ResponseEntity<ResponseObjDto> getOrders(String city, String region, BigDecimal minPrice, BigDecimal maxPrice, String currency) {
        return ResponseEntity.ok(orderService.getOrders(city,region,minPrice,maxPrice,currency));
    }
    @PreAuthorize("hasRole('CAR_SELLER')")
    @Override
    public ResponseEntity<ResponseOrderDto> postOrder(CreateOrderDto createOrderDto) {
        return ResponseEntity.ok(orderService.createOrder(createOrderDto));
    }
    @PreAuthorize("hasRole('CAR_SELLER')")
    @Override
    public ResponseEntity<ResponseOrderDto> updateOrder(Integer id, UpdateOrderDto updateOrderDto) {
        return ResponseEntity.ok(orderService.updateOrder(id,updateOrderDto));
    }

}
