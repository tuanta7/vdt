package com.vdt.fosho.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateDTO {
    @Range(min = -180, max = 180, message = "Longitude must be between -180 and 180")
    private double longitude;

    @Range(min = -90, max = 90, message = "Latitude must be between -90 and 90")
    private double latitude;
}
