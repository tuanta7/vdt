package com.vdt.fosho.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
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

    public User(Long id, String email, String username, String avatarUrl, List<Restaurant> restaurants, List<ShippingAddress> shippingAddresses) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.restaurants = restaurants;
        this.shippingAddresses = shippingAddresses;
    }
}
