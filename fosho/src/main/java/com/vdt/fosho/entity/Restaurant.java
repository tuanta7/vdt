package com.vdt.fosho.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "phone")
    private String phone;

    @Column(name = "rating")
    @Range(min = 0, max = 5)
    private int rating;

    @Column(name = "address")
    private String address;

    @Column(name = "coordinates", columnDefinition = "Point")
    private Point coordinates;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private User owner;

    public Restaurant(String name, String address, String phone, Point coordinates, String logoUrl, User owner) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.coordinates = coordinates;
        this.logoUrl = logoUrl;
        this.owner = owner;
    }
}
