package com.vdt.fosho.controller;

import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.service.RestaurantService;
import com.vdt.fosho.utils.JSendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final RestaurantService restaurantService;

    @GetMapping("/{user_id}/restaurants")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, List<RestaurantDTO>>> getAllUserRestaurants(
            @PathVariable("user_id") Long userId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (!Objects.equals(user.getId(), userId)){
            throw new IllegalArgumentException("Can not get restaurants of other user");
        }

        List<Restaurant> restaurants = restaurantService.getRestaurantsByOwnerId(userId);
        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            restaurantDTOs.add(restaurantService.toDTO(restaurant));
        }

        HashMap<String, List<RestaurantDTO>> data = new HashMap<>();
        data.put("restaurants", restaurantDTOs);

        return JSendResponse.success(data);
    }

    private boolean isOwner(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return restaurant.getOwner().getId().equals(user.getId());
    }
}
