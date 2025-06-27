package org.copyria2.order_service.services;

import lombok.RequiredArgsConstructor;
import org.copyria2.order_service.entity.OrderView;
import org.copyria2.order_service.repository.OrderViewRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderViewService {
    private final OrderViewRepository viewRepository;

    public void incrementView(Integer orderId) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate monthStart = today.withDayOfMonth(1);

        OrderView view = viewRepository
                .findById(orderId)
                .orElseGet(() -> OrderView.builder()
                        .orderId(orderId)
                        .viewsTotal(0)
                        .viewsPerDay(0)
                        .viewsWeek(0)
                        .viewsMonth(0)
                        .lastDay(today)
                        .lastWeekStart(weekStart)
                        .lastMonthStart(monthStart).build()
                );

        if (!today.equals(view.getLastDay())) {
            view.setViewsPerDay(0);
            view.setLastDay(today);
        }
        if (!weekStart.equals(view.getLastWeekStart())) {
            view.setViewsWeek(0);
            view.setLastWeekStart(weekStart);
        }
        if (!monthStart.equals(view.getLastMonthStart())) {
            view.setViewsMonth(0);
            view.setLastMonthStart(monthStart);
        }

        view.setViewsTotal(view.getViewsTotal() + 1);
        view.setViewsPerDay(view.getViewsPerDay() + 1);
        view.setViewsWeek(view.getViewsWeek() + 1);
        view.setViewsMonth(view.getViewsMonth() + 1);

        viewRepository.save(view);
    }
}
