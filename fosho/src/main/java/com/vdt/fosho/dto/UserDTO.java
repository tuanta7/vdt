package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.entity.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String fullName;

    private String email;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("shipping_addresses")
    private List<ShippingAddress> shippingAddresses;
}
