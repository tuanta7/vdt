package com.vdt.fosho.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dish_image")
public class DishImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;
}
