package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {

    private Long id;

    private Long orderId;

    private int quantity;

    @JsonProperty("dish_id")
    private Long dishId;

    @JsonProperty("dish")
    private DishDTO dishDTO;

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonIgnore
    private User user;

    @JsonIgnore
    private Order order;

    @JsonIgnore
    private Dish dish;
}
