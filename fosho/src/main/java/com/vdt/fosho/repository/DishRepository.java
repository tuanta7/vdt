package com.vdt.fosho.repository;

import com.vdt.fosho.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long>{

    public List<Dish> findByRestaurantId(Long restaurantId);

}
