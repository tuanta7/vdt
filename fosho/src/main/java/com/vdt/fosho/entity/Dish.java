package com.vdt.fosho.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "discount")
    private double discount;

    @Column(name = "rating")
    private double rating;

    @Column(name="unit")
    private String unit; // Cốc, phần, 1kg, 1 lít, 1 hộp,...

    @Column(name = "sold")
    private int sold;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "thumbnail_public_id")
    private String thumbnailPublicId;

    @Column(name = "stock")
    private int stock;

    @OneToMany
    private List<DishImage> images;

    @OneToMany(mappedBy = "dish")
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
