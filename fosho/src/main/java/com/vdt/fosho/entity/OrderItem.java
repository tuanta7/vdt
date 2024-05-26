package com.vdt.fosho.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "order_id")
        private Order order;

        @ManyToOne
        @JoinColumn(name = "dish_id")
        private Dish dish;

        @Column(name = "quantity")
        private int quantity;
}
