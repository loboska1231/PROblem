package org.copyria2.car_service.services;

import lombok.RequiredArgsConstructor;
import org.copyria2.car_service.api.event.model.CarDeletedEventPayload;
import org.copyria2.car_service.api.event.producer.ICarEventsProducer;
import org.copyria2.car_service.entity.CarEntity;
import org.copyria2.car_service.mapper.CarMapper;
import org.copyria2.car_service.repository.CarRepository;

import org.copyria2.carservice.api.rest.model.CarResponseDto;
import org.copyria2.carservice.api.rest.model.CreateCarDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ICarEventsProducer carEventsProducer;
    public List<CarResponseDto> findAll(String brand, String model) {
        List<CarEntity> cars ;
        if(brand!=null && model !=null ){
            cars = carRepository.findAllByBrandAndModel(brand, model);
        } else if (brand !=null) {
            cars = carRepository.findAllByBrand(brand);
        }
        else cars = carRepository.findAll();
        return cars.stream().map(carMapper::toResponseDto).toList();
    }
    public CarResponseDto findById(Integer id) {
        return carMapper.toResponseDto(carRepository.findById(id).get());
    }
    public CarResponseDto createCar(CreateCarDto carDto){
        CarEntity car = carMapper.toEntity(carDto);
        var saved = carRepository.save(car);
        return carMapper.toResponseDto(saved);
    }
    public void DeleteCar(Integer id) {
        carRepository.deleteById(id);
        //
        CarDeletedEventPayload event = new CarDeletedEventPayload().withCarId(id);
        carEventsProducer.onCarDeletedEvent(event);
    }

    public CarResponseDto getCarByOrderId(Integer id) {
        return carMapper.toResponseDto(carRepository.findByOrderId(id));
    }
}
