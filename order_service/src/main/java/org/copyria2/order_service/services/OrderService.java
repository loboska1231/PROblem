package org.copyria2.order_service.services;

import lombok.RequiredArgsConstructor;
import org.copyria2.order_service.mapper.OrderMapper;
import org.copyria2.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
}
