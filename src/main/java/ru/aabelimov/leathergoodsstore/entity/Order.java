package ru.aabelimov.leathergoodsstore.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToOne
    private PromoCode promoCode;
    private OrderStatus status;

//    @ManyToMany
//    @JoinTable(
//            name = "orders_products",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "id")
//    )
//    private List<OrderProduct> orderProducts;
}
