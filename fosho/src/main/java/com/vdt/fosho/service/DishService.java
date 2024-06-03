package com.vdt.fosho.service;

import com.vdt.fosho.dto.DishDTO;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishService {

        private final DishRepository dishRepository;

        public List<Dish> getAllDishesByRestaurantId(Long restaurantId) {
            return dishRepository.findByRestaurantId(restaurantId);
        }

        public Dish createDish(Dish dish) {
            return dishRepository.save(dish);
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
                    .available(dish.isAvailable())
                    .build();
        }
}
