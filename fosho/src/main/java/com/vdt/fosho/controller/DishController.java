package com.vdt.fosho.controller;

import com.vdt.fosho.dto.DishDTO;
import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.exception.ForbiddenException;
import com.vdt.fosho.service.DishService;
import com.vdt.fosho.service.RestaurantService;
import com.vdt.fosho.utils.JSendResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class DishController {

    RestaurantService restaurantService;
    DishService dishService;

    @GetMapping("/restaurants/{restaurant_id}/dishes")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, List<DishDTO>>> getAllRestaurantDishes(
            @PathVariable("restaurant_id") Long restaurantId
    ) {
        List<Dish> dishes = dishService.getAllDishesByRestaurantId(restaurantId);
        List<DishDTO> dishDTOs = new ArrayList<>();
        for (Dish dish : dishes) {
            dishDTOs.add(dishService.toDTO(dish));
        }

        HashMap<String, List<DishDTO>> data = new HashMap<>();
        data.put("dishes", dishDTOs);
        return JSendResponse.success(data);
    }

    @PostMapping("/restaurants/{restaurant_id}/dishes")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, DishDTO>> createDish(
            @PathVariable("restaurant_id") Long restaurantId,
            @RequestBody DishDTO dishDTO
    ) {
        Restaurant restaurant = verifyOwner(restaurantId);
        dishDTO.setRestaurant(restaurant);

        Dish createdDish = dishService.createDish(dishDTO.toDish());
        HashMap<String, DishDTO> data = new HashMap<>();
        data.put("dish", dishService.toDTO(createdDish));
        return JSendResponse.success(data);
    }

    private Restaurant verifyOwner(Long restaurantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!Objects.equals(restaurant.getOwner().getId(), user.getId())) {
            throw new ForbiddenException("You are not the owner of this restaurant");
        }
        return restaurant;
    }
}
