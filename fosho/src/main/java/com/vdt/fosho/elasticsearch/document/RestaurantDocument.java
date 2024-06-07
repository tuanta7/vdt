package com.vdt.fosho.elasticsearch.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "restaurants")
public class RestaurantDocument {
    @Id
    private Long id;

    @Field(name = "name")
    private String name;

    @Field(name = "address")
    private String address;

    @Field(name = "logo_url")
    @JsonProperty("logo_url")
    private String logoUrl;

    @Field(name = "is_active")
    @JsonProperty("is_active")
    private boolean isActive;

    @Field(name = "open_time")
    @JsonProperty("open_time")
    private String openTime;

    @Field(name = "close_time")
    @JsonProperty("close_time")
    private String closeTime;

    @Field(name = "rating")
    private double rating;

    @Field(name = "phone")
    private String phone;

    @Field(name = "latitude")
    private double latitude;

    @Field(name = "longitude")
    private double longitude;
}
