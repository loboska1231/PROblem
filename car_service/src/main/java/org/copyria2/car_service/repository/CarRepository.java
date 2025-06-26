package org.copyria2.car_service.repository;

import org.copyria2.car_service.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {
    List<CarEntity> findAllByBrand(String brand);
    List<CarEntity> findAllByBrandAndModel(String brand, String model);
    CarEntity findByOrderId(Integer orderId);
}
