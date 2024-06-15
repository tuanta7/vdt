package com.vdt.fosho.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long id;

    private String message;

    private boolean seen;

    @JsonProperty("user_name")
    private String user;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("restaurant_name")
    private String restaurant;

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("from_user")
    private boolean fromUserToRestaurant;
}
