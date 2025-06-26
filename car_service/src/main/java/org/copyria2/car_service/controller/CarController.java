package org.copyria2.car_service.controller;

import lombok.RequiredArgsConstructor;
import org.copyria2.car_service.services.CarService;
import org.copyria2.carservice.api.rest.model.CreateCarDto;
import org.copyria2.carservice.api.rest.model.CarResponseDto;
import org.copyria2.carservice.api.rest.controller.CarApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController implements CarApi {
    private final CarService carService;
    @Override
    public ResponseEntity<CarResponseDto> createCar(CreateCarDto createCarDto) {
        return ResponseEntity.ok(carService.createCar(createCarDto));
    }

    @Override
    public ResponseEntity<Void> deleteCarById(Integer id) {
        carService.DeleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CarResponseDto> getCarById(Integer id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @Override
    public ResponseEntity<CarResponseDto> getCarByOrderId(Integer id) {
        return ResponseEntity.ok(carService.getCarByOrderId(id));
    }

    @Override
    public ResponseEntity<List<CarResponseDto>> getCars(String brand, String model) {
        return ResponseEntity.ok(carService.findAll(brand, model));
    }
}
