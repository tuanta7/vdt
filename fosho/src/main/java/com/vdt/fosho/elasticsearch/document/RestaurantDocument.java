package com.vdt.fosho.elasticsearch.document;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Entity
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

    @Field(name = "latitude")
    private double latitude;

    @Field(name = "longitude")
    private double longitude;
}
