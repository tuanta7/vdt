package com.vdt.fosho.service;

import com.vdt.fosho.dto.DishDTO;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.exception.ResourceNotFoundException;
import com.vdt.fosho.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }


    public Dish getDishById(Long dishId) {
        Optional<Dish> result = dishRepository.findById(dishId);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Dish not found with id: " + dishId);
        }
        return result.get();
    }


    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }


    public List<Dish> getAllDishesByRestaurantId(Long restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId);
    }


    public Dish updateDishThumbnail(Long dishId, String url, String publicId) {
        Optional<Dish> result = dishRepository.findById(dishId);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Dish not found with id: " + dishId);
        }
        Dish dish = result.get();
        dish.setThumbnailUrl(url);
        dish.setThumbnailPublicId(publicId);
        dishRepository.save(dish);
        return dish;
    }

    public DishDTO toDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .price(dish.getPrice())
                .discount(dish.getDiscount())
                .unit(dish.getUnit())
                .sold(dish.getSold())
                .thumbnailUrl(dish.getThumbnailUrl())
                .stock(dish.getStock())
                .build();
    }
}
