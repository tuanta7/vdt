package com.vdt.fosho.dto;

import com.vdt.fosho.entity.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class RestaurantDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid phone number")
    private String phone;

    private int longitude;
    private int latitude;

    public RestaurantDTO(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Address is required") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address is required") String address) {
        this.address = address;
    }

    public @NotBlank(message = "Phone is required") @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid phone number") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Phone is required") @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid phone number") String phone) {
        this.phone = phone;
    }

    public Restaurant toRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        return restaurant;
    }
}
