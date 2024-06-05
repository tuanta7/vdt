package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.entity.Order;
import com.vdt.fosho.entity.OrderItem;
import com.vdt.fosho.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {

    private Long id;

    private Long orderId;

    @JsonProperty("dish_id")
    private Long dishId;

    @JsonProperty("dish")
    private DishDTO dishDTO;

    private int quantity;

    @JsonIgnore
    private User user;

    @JsonIgnore
    private Order order;

    @JsonIgnore
    @JsonProperty("_")
    private Dish dish;

    public OrderItem toEntity() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setQuantity(quantity);
        orderItem.setOrder(order);
        orderItem.setDish(dish);
        orderItem.setUser(user);
        return orderItem;
    }
}
