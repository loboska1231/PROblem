package org.copyria2.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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

    private String brand;
    private String model;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "views_order_id")
    private OrderView views; // prem
    private BigDecimal avgPriceByRegion; //prem
    private BigDecimal avgPrice; //prem
}
