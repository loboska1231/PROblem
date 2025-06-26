package org.copyria2.order_service.repository;

import org.copyria2.order_service.client.rest.model.ResponseOrderViewsDto;
import org.copyria2.order_service.entity.OrderView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderViewRepository extends JpaRepository<OrderView, Integer> {
}
