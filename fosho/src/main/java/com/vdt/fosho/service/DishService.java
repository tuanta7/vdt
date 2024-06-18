package com.vdt.fosho.service;

import com.vdt.fosho.dto.DishDTO;
import com.vdt.fosho.elasticsearch.document.DishDocument;
import com.vdt.fosho.elasticsearch.repository.DishDocumentRepository;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.exception.ResourceNotFoundException;
import com.vdt.fosho.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final DishDocumentRepository dishDocumentRepository;

    public boolean replicateData(String op, DishDocument dishDocument) {
        if(op.equals("c") || op.equals("u")) {
            System.out.println("Replicating data to Elasticsearch....");
            dishDocumentRepository.save(dishDocument);
            return true;
        } else if(op.equals("d")) {
            dishDocumentRepository.deleteById(dishDocument.getId());
        }
        return true;
    }

    public Page<DishDocument> getAllDishes(String search, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        if (search.isEmpty()) {
            System.out.println("Searching all dishes in Elasticsearch");
            return dishDocumentRepository.findAll(pageable);
        }
        return dishDocumentRepository.findByName(search, pageable);  // Elasticsearch
    }

    public Dish getDishById(Long dishId) {
        Optional<Dish> result = dishRepository.findById(dishId);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Dish not found with id: " + dishId);
        }
        return result.get();
    }


    public Dish createDish(DishDTO dishDTO) {
        Dish dish = toEntity(dishDTO);
        return dishRepository.save(dish);
    }

    public void save(Dish dish) {
        dishRepository.save(dish);
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

    // Accepted input fields for create/update: name, description, price, discountPrice, unit, stock
    public Dish toEntity(DishDTO dishDTO) {
        return Dish.builder()
                .name(dishDTO.getName())
                .description(dishDTO.getDescription())
                .price(dishDTO.getPrice())
                .discount(dishDTO.getDiscount())
                .unit(dishDTO.getUnit())
                .restaurant(dishDTO.getRestaurant())
                .stock(dishDTO.getStock())
                .build();
    }


}
