package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.entity.Restaurant;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Price is required")
    private double price;

    private double discount;

    @NotBlank(message = "Unit is required")
    private String unit;

    private int sold;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @Range(min = 0, message = "Stock must be greater than or equal to 0")
    private int stock;

    @JsonProperty("restaurant")
    private RestaurantDTO restaurantDTO;

    @JsonIgnore
    private Restaurant restaurant;

    // Accepted input fields for create/update: name, description, price, discountPrice, unit, stock
    public Dish toEntity() {
        return Dish.builder()
                .name(name)
                .description(description)
                .price(price)
                .discount(discount)
                .unit(unit)
                .restaurant(restaurant)
                .stock(stock)
                .build();
    }
}
