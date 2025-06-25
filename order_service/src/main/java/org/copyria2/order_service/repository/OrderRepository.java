package org.copyria2.order_service.repository;

import org.copyria2.order_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByCity(String city);
    List<OrderEntity> findByRegion(String Region);
    List<OrderEntity> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal price, BigDecimal price2);
    List<OrderEntity> findAllByPriceLessThanEqual(BigDecimal price);
    List<OrderEntity> findAllByPriceGreaterThanEqual(BigDecimal price);
}
