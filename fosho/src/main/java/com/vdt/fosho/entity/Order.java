package com.vdt.fosho.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "shipping_fee")
    private double shippingFee;

    @Column(name = "total_discount")
    private double totalDiscount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT NOW()")
    private Date createdAt;

    @Column(name = "delivered_at", columnDefinition = "DATETIME")
    private Date deliveredAt;
}
