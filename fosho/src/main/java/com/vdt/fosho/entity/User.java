package com.vdt.fosho.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(mappedBy = "owner")
    private List<Restaurant> restaurants;

    @OneToMany(mappedBy = "user")
    private List<ShippingAddress> shippingAddresses;
}
