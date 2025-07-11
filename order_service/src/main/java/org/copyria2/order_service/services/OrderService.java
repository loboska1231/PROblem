package org.copyria2.order_service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.copyria2.order_service.client.rest.api.CarApi;
import org.copyria2.order_service.client.rest.model.ResponseChangeDto;
import org.copyria2.order_service.client.rest.model.CreateOrderDto;
import org.copyria2.order_service.client.rest.model.ResponseObjDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderCarDto;
import org.copyria2.order_service.client.rest.model.ResponseOrderDto;
import org.copyria2.order_service.client.rest.model.UpdateOrderDto;
import org.copyria2.order_service.dto.MailDto;
import org.copyria2.order_service.entity.OrderEntity;
import org.copyria2.order_service.entity.OrderView;
import org.copyria2.order_service.mapper.CarMapper;
import org.copyria2.order_service.mapper.ChangeMapper;
import org.copyria2.order_service.mapper.OrderMapper;
import org.copyria2.order_service.mapper.OrderViewMapper;
import org.copyria2.order_service.repository.ChangeRepository;
import org.copyria2.order_service.repository.OrderRepository;
import org.copyria2.order_service.repository.OrderViewRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ChangeRepository changeRepository;
    private final OrderViewRepository orderViewRepository;

    private final OrderViewService orderViewService;
    private final UserService userService;
    private final MailService mailService;

    private final OrderMapper orderMapper;
    private final ChangeMapper changeMapper;
    private final OrderViewMapper orderViewMapper;
    private final CarMapper carMapper;
    @Qualifier("serviceAuthCarApi")
    private final CarApi carApi;
    public void deleteOrder(Integer id) {
        var car = carApi.getCarByOrderId(id);
        carApi.deleteCarById(car.getId());
        orderRepository.deleteById(id);
    }
    public ResponseOrderDto getOrder(Integer id, String currency){
        OrderEntity order = orderRepository.findById(id).get();
        orderViewService.incrementView(order.getId());
        OrderView views = orderViewRepository.findById(order.getId()).get();
        var car = carApi.getCarByOrderId(id);
        if(currency != null &&(currency.equals("USD") || currency.equals("EUR") || currency.equals("UAH")) ) {
            order = converter(order,currency);
        }
        log.info("isPremium acc: {}", userService.isPremium());
        if(!userService.isPremium()) {
            views = null;
            order.setAvgPrice(null);
            order.setAvgPriceByRegion(null);
        }
        return orderMapper
                .ToResponseOrderDto(order,
                        carMapper.ToOrderCarResponseDto(car),
                        orderViewMapper.toDto(views)
                        );
    }
   public OrderEntity converter(OrderEntity order, String currency){
       String curr = order.getCurrency();
       BigDecimal fromRate = curr.equals("UAH") ? BigDecimal.valueOf(1.0): changeRepository.findByCurrency(curr).getBuy();
       BigDecimal toRate = currency.equals("UAH") ? BigDecimal.valueOf(1.0) : changeRepository.findByCurrency(currency).getBuy();
       BigDecimal converted = order.getPrice().multiply(fromRate).divide(toRate, RoundingMode.HALF_UP);
       order.setPrice(converted);
       order.setCurrency(currency);
       return order;
   }
    public ResponseOrderDto createOrder(CreateOrderDto dto){
        if(!dto.getCurrency().equals("USD") && !dto.getCurrency().equals("EUR") && !dto.getCurrency().equals("UAH")){
            return new ResponseOrderDto() ;
        }
        String email = userService.getEmail();
        log.info("email: {}", email);
        boolean check = orderRepository.existsByOwnerEmail(email);
        log.info("check: {}", check);
        if(
                !userService.isPremium()
                && check
        ){
            return new ResponseOrderDto() ;
        }
        OrderEntity order = orderMapper.ToEntity(dto);
        order.setAvgPrice(orderRepository.findAveragePrice(order.getBrand(),order.getModel()));
        order.setAvgPriceByRegion(orderRepository.findAveragePriceByRegion(order.getRegion(),order.getBrand(),order.getModel()));
        // считает до n-1 --->>> order[0]+order[1] +order[2] ...+ order[n-1] без order[n]
        // если n = 0 то null
        // n =1 , avgPrice = order.getPrice()
        log.info("avgPrice : {}", order.getAvgPrice());
        log.info("avgPriceByRegion : {}", order.getAvgPriceByRegion());
        order.setOwnerEmail(userService.getEmail());
        OrderEntity save = orderRepository.save(order);
        orderViewService.incrementView(save.getId());
        OrderView views = orderViewRepository.findById(save.getId()).get();
        save = orderMapper.updateOrderViews(save, views);

        var car = dto.getCar();
        var carDto = carMapper.ToCreateCarDto(car, save.getId());
        carApi.createCar(carDto);
        ResponseOrderDto response = orderMapper
                .ToResponseOrderDto(save,
                        carMapper.ToOrderCarResponseDto(order.getBrand(), order.getModel()),
                        orderViewMapper.toDto(views)
                );
        mailService.sendEmail(MailDto.builder()
                .to(order.getOwnerEmail())
                .title("Order Created")
                .message("order : "+ response)
                .build());
        return response;
    }
    public ResponseObjDto getOrders(String city, String region, BigDecimal minPrice, BigDecimal maxPrice, String currency){
        List<OrderEntity> orders;
        if(
                city != null && region != null
                        && minPrice != null && maxPrice != null
        ) {
            orders = orderRepository.findAllByCityAndPriceGreaterThanEqualAndPriceLessThanEqual(city, minPrice, maxPrice);
        } else if(
                region != null && minPrice != null && maxPrice != null
        ){
            orders = orderRepository.findAllByRegionAndPriceGreaterThanEqualAndPriceLessThanEqual(region, minPrice, maxPrice);
        } else if (minPrice != null && maxPrice != null) {
            orders = orderRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice);
        } else if (minPrice != null ) {
            orders = orderRepository.findAllByPriceGreaterThanEqual(minPrice);
        } else if(maxPrice != null) {
            orders = orderRepository.findAllByPriceLessThanEqual(maxPrice);
        } else {
            orders = orderRepository.findAll();
        }
        orders = orders.stream().map(order->{
            if(currency!= null && !order.getCurrency().equals(currency)
                    && (currency.equals("USD") || currency.equals("EUR") || currency.equals("UAH"))
            ){
                order = converter(order,currency);
            }

            return order;
        }).toList();

        List<ResponseOrderDto> ordersDtos = orders.stream()
                .filter(order->order.getStatus().equals("ACTIVE") || order.getStatus().equals("UPDATED") || order.getStatus().equals("CREATED"))
                .map(order -> {
                    var car = carApi.getCarByOrderId(order.getId());
                    ResponseOrderCarDto carDto = carMapper.ToOrderCarResponseDto(car);
                    orderViewService.incrementView(order.getId());
                    OrderView views = orderViewRepository.findById(order.getId()).get();
                    if(!userService.isPremium()) {
                        views = null;
                        order.setAvgPrice(null);
                        order.setAvgPriceByRegion(null);
                        log.info("isPremium: {}",  userService.isPremium());
                    }
                    return orderMapper.ToResponseOrderDto(order, carDto, orderViewMapper.toDto(views));
                }).toList();
        List<ResponseChangeDto> changes = changeRepository.findAll().stream().map(changeMapper::toChangeDto).toList();
        return orderMapper.toResponseObj(ordersDtos, changes);
    }
    @Transactional
    public ResponseOrderDto updateOrder(Integer id, UpdateOrderDto dto){
        return orderRepository.findById(id)
                .map(order -> {
                    orderMapper.updateOrderEntity(order, dto);
                    if(order.getEditedTimes()>=3){
                        updateOrderStatus(id,"NonActive");
                    }
                    var car = carApi.getCarByOrderId(order.getId());
                    orderViewService.incrementView(order.getId());
                    OrderView views = orderViewRepository.findById(order.getId()).get();

                    return orderMapper
                            .ToResponseOrderDto(order,
                                    carMapper.ToOrderCarResponseDto(car),
                                    orderViewMapper.toDto(views)
                                    );
                }).get();
    }
    @Transactional
    public OrderEntity updateOrderViews(Integer id, OrderView views){
        return orderRepository.findById(id)
                .map(order -> {
                    orderMapper.updateOrderViews(order, views);
                    orderViewService.incrementView(order.getId());
                    OrderView orderViews = orderViewRepository.findById(order.getId()).get();
                    order.setViews(orderViews);
                    return order;
                }).get();
    }
    @Transactional
    public void updateOrderStatus(Integer id,String status) {
        orderRepository.findById(id).ifPresent(order -> {
            if(order.getEditedTimes() !=3){
                order.setStatus(status);
                mailService.sendEmail(MailDto.builder()
                        .to(order.getOwnerEmail())
                        .title("Order Status updated")
                        .message("order status : "+ status)
                        .build());
            }
            else{
                order.setStatus("NonActive");
                mailService.sendEmail(MailDto.builder()
                        .to(order.getOwnerEmail())
                        .title("Order Status updated")
                        .message("order status : "+ "NonActive")
                        .build());
            }

        });
    }
    public OrderEntity getOrderByCarId(Integer carId){
        Integer orderId = carApi.getCarById(carId).getOrderId();
        return orderRepository.findById(orderId).get();
    }
}
