package com.vdt.fosho.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.entity.ShippingAddress;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.utils.GeoUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddressDTO {
        private Long id;

        @NotBlank(message = "Name is required")
        private String name;

        @NotBlank(message = "Address is required")
        private String address;

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid phone number")
        private String phone;

        @NotBlank(message = "Receiver name is required")
        @JsonProperty("receiver_name")
        private String receiverName;

        @Range(min = -90, max = 90)
        private double latitude;

        @Range(min = -180, max = 180)
        private double longitude;

        @JsonIgnore
        private User user;

        public ShippingAddress toEntity() {
            return ShippingAddress.builder()
                    .id(id)
                    .phone(phone)
                    .receiverName(receiverName)
                    .name(name)
                    .address(address)
                    .coordinates(GeoUtils.createPoint(latitude, longitude))
                    .user(user)
                    .build();
        }
}
