package com.vdt.fosho.entity;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "shipping_address")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "coordinates", columnDefinition = "Point")
    private Point coordinates;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
