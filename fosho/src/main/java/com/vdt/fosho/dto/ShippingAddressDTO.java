package com.vdt.fosho.dto;

import com.vdt.fosho.entity.ShippingAddress;
import com.vdt.fosho.utils.GeoUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.locationtech.jts.geom.Point;

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

        @Range(min = -90, max = 90)
        private double latitude;

        @Range(min = -180, max = 180)
        private double longitude;

        public ShippingAddress toShippingAddress() {
            return ShippingAddress.builder()
                    .id(id)
                    .name(name)
                    .address(address)
                    .coordinates(GeoUtils.createPoint(latitude, longitude))
                    .build();
        }
}
