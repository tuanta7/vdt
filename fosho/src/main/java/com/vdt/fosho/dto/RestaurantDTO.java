package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.utils.GeoUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
        restaurant.setCoordinates(GeoUtils.createPoint(latitude, longitude));
        return restaurant;
    }
}
