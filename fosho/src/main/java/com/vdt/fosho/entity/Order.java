package com.vdt.fosho.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "note")
    private String note;

    @Column(name = "destination")
    private String destination;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private ShippingAddress shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod; // Right now, only support COD 😓

    // Timestamps
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime createdAt;

    @Column(name = "confirmed_at", columnDefinition = "DATETIME")
    private LocalDateTime confirmedAt;

    @Column(name = "pickup_time", columnDefinition = "DATETIME")
    private LocalDateTime pickupTime;

    @Column(name = "delivered_at", columnDefinition = "DATETIME")
    private LocalDateTime deliveredAt;
}
