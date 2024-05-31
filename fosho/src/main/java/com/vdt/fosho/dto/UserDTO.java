package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.entity.ShippingAddress;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("shipping_addresses")
    private List<ShippingAddress> shippingAddresses;
}
