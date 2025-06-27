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
    @Column(name="total_views")
    private Integer viewsTotal;

    private Integer viewsPerDay;
    @Column(name="views_per_week")
    private Integer viewsWeek;
    @Column(name="views_per_month")
    private Integer viewsMonth;

    private LocalDate lastDay;
    private LocalDate lastWeekStart;
    private LocalDate lastMonthStart;

}