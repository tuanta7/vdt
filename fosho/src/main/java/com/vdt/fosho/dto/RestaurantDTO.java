package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.utils.GeoUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid phone number")
    private String phone;

    @JsonProperty("logo_url")
    private String logoUrl;

    @JsonProperty("is_open")
    private boolean isOpen;

    @JsonProperty("open_time")
    @NotBlank(message = "Open time is required")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Invalid time format")
    private String openTime;

    @JsonProperty("close_time")
    @NotBlank(message = "Close time is required")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Invalid time format")
    private String closeTime;

    private int rating;

    @Range(min = -90, max = 90)
    private double latitude;
    
    @Range(min = -180, max = 180)
    private double longitude;

    private User owner;

    // Accepted input fields: name, address, phone, latitude, longitude
    public Restaurant toRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        restaurant.setOpenTime(openTime);
        restaurant.setCloseTime(closeTime);
        restaurant.setCoordinates(GeoUtils.createPoint(latitude, longitude));
        restaurant.setOwner(owner);
        return restaurant;
    }
}
