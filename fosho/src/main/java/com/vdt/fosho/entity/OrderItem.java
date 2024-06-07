package com.vdt.fosho.entity;

import com.vdt.fosho.dto.OrderItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "order_id")
        private Order order; // null means that the item is only in user's cart (not in any order yet)

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "dish_id", nullable = false)
        private Dish dish;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(name = "quantity")
        private int quantity;

        public OrderItemDTO toDTO() {
                return OrderItemDTO.builder()
                        .id(this.id)
                        .order(this.order)
                        .quantity(this.quantity)
                        .dishDTO(this.dish.toDTO())
                        .restaurantId(this.dish.getRestaurant().getId())
                        .restaurantName(this.dish.getRestaurant().getName())
                        .build();
        }
}
