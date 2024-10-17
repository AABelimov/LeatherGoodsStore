package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    private Double totalCost;
    private Integer totalQuantity;
    private String comment;
    private String communicationType;
    private String communicationData;
    private String deliveryMethod;
    private String fio;
    private String address;
    private LocalDateTime orderDate;
    private String paymentId;

    @ManyToOne
    private PromoCode promoCode;
    private OrderStatus status;
}
