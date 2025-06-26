package org.copyria2.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "views")
public class OrderView {

    @Id
    private Integer orderId;

    private Integer viewsTotal;

    private Integer viewsPerDay;
    private Integer viewsWeek;
    private Integer viewsMonth;

    private LocalDate lastDay;
    private LocalDate lastWeekStart;
    private LocalDate lastMonthStart;

}