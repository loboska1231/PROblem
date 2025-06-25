package org.copyria2.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderPremiumDto;
import org.copyria2.order_service.client.rest.model.UpdateOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.copyria2.order_service.client.rest.controller.OrdersApi;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {
//    @Override
//    public ResponseEntity<Void> deleteOrder(Long id) {
//        return OrdersApi.super.deleteOrder(id);
//    }
//
//    @Override
//    public ResponseEntity<ResponseOrderPremiumDto> getOrder(Long id, String curr) {
//        return OrdersApi.super.getOrder(id, curr);
//    }
//
//    @Override
//    public ResponseEntity<List<ResponseOrderDto>> getOrders(String city, String region, Double minPrice, Double maxPrice, String currency) {
//        return OrdersApi.super.getOrders(city, region, minPrice, maxPrice, currency);
//    }
//
//    @Override
//    public ResponseEntity<ResponseOrderDto> postOrder(CreateOrderDto createOrderDto) {
//        return OrdersApi.super.postOrder(createOrderDto);
//    }
//
//    @Override
//    public ResponseEntity<ResponseOrderDto> updateOrder(Long id, UpdateOrderDto updateOrderDto) {
//        return OrdersApi.super.updateOrder(id, updateOrderDto);
//    }
}
