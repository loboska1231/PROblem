package org.copyria2.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal price;
    private String currency;
    private String city;
    private String region;
    private String status;
    private String ownerEmail;
    private int editedTimes;
    private int totalViews; // prem
    private int viewsPerDay; //prem
    private int viewsPerWeek; //prem
    private int viewsPerMonth; //prem
    private BigDecimal avgPriceByRegion; //prem
    private BigDecimal avgPrice; //prem
}
