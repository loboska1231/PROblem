package org.copyria2.order_service.repository;

import org.copyria2.order_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    List<OrderEntity> findAllByRegionAndPriceGreaterThanEqualAndPriceLessThanEqual(String city, BigDecimal minPrice, BigDecimal maxPrice);
    List<OrderEntity> findAllByCityAndPriceGreaterThanEqualAndPriceLessThanEqual(String city, BigDecimal priceAfter, BigDecimal priceBefore);
    @Query("SELECT AVG(o.price) FROM OrderEntity o WHERE o.brand = :brand AND o.model = :model AND o.price IS NOT NULL")
    BigDecimal findAveragePrice(@Param("brand") String brand, @Param("model") String model);

    @Query("SELECT AVG(o.price) FROM OrderEntity o WHERE o.region = :region AND o.brand = :brand AND o.model = :model AND o.price IS NOT NULL")
    BigDecimal findAveragePriceByRegion(@Param("region") String region, @Param("brand") String brand, @Param("model") String model);

    @Query("SELECT AVG(o.price) FROM OrderEntity o WHERE o.city = :city AND o.brand = :brand AND o.model = :model AND o.price IS NOT NULL")
    BigDecimal findAveragePriceByCity(@Param("city") String city, @Param("brand") String brand, @Param("model") String model);


}
