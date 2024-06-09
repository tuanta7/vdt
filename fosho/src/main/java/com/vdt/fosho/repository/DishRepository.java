package com.vdt.fosho.repository;

import com.vdt.fosho.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long>{

     List<Dish> findByRestaurantId(Long restaurantId);

}
