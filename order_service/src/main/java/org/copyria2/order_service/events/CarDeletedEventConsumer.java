package org.copyria2.order_service.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.copyria2.order_service.services.OrderService;
import org.springframework.stereotype.Component;
import org.copyria2.order_service.api.consumer.IOnCarDeletedEventConsumerService;
import org.copyria2.order_service.api.model.CarDeletedEventPayload;
@Component
@RequiredArgsConstructor
@Slf4j
public class CarDeletedEventConsumer implements IOnCarDeletedEventConsumerService {
    private final OrderService orderService;
    @Override
    public void onCarDeletedEvent(CarDeletedEventPayload payload, CarDeletedEventPayloadHeaders headers) {
        log.info("Received event: {}", payload);
        Integer orderId = orderService.getOrderByCarId(payload.getCarId()).getId();
        orderService.updateOrderStatus(orderId ,"CANCELLED");
    }
}
