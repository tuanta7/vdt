package com.vdt.fosho.controller;

import com.vdt.fosho.dto.AddressDTO;
import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.service.RestaurantService;
import com.vdt.fosho.utils.JSendResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, List<RestaurantDTO>>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        List<RestaurantDTO> restaurantDTOs = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            restaurantDTOs.add(restaurantService.toDTO(restaurant));
        }
        HashMap<String, List<RestaurantDTO>> data = new HashMap<>();
        data.put("restaurants", restaurantDTOs);
        return JSendResponse.success(data);
    }


    @PostMapping("/restaurants")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, RestaurantDTO>> createRestaurant(
            @Valid @RequestBody RestaurantDTO restaurantDTO
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        restaurantDTO.setOwner((User) authentication.getPrincipal());

        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurantDTO.toRestaurant());
        HashMap<String, RestaurantDTO> data = new HashMap<>();
        data.put("restaurant", restaurantService.toDTO(createdRestaurant));
        return JSendResponse.success(data);
    }


    @GetMapping("/restaurants/{restaurant_id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, RestaurantDTO>> getRestaurantById(@PathVariable("restaurant_id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        HashMap<String, RestaurantDTO> data = new HashMap<>();
        data.put("restaurant", restaurantService.toDTO(restaurant));
        return JSendResponse.success(data);
    }


    @PutMapping("/restaurants/{restaurant_id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, RestaurantDTO>> updateRestaurant(
            @PathVariable("restaurant_id") Long id,
            @Valid @RequestBody RestaurantDTO restaurantDTO
    ) {
        if (isOwner(id)) {
            throw new IllegalArgumentException("Access denied, you are not the owner of this restaurant");
        }
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDTO.toRestaurant());
        HashMap<String, RestaurantDTO> data = new HashMap<>();
        data.put("restaurant", restaurantService.toDTO(updatedRestaurant));
        return JSendResponse.success(data);
    }

    @PatchMapping("/restaurants/{restaurant_id}/coordinates")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, RestaurantDTO>> updateRestaurantCoordinates(
            @PathVariable("restaurant_id") Long id,
            @Valid @RequestBody AddressDTO addressDTO
        ) {
        if (isOwner(id)) {
            throw new IllegalArgumentException("Access denied, you are not the owner of this restaurant");
        }
        Restaurant updatedRestaurant = restaurantService.updateRestaurantAddress(
                id,
                addressDTO.getAddress(),
                addressDTO.getLongitude(),
                addressDTO.getLatitude()
        );
        HashMap<String, RestaurantDTO> data = new HashMap<>();
        data.put("restaurant", restaurantService.toDTO(updatedRestaurant));
        return JSendResponse.success(data);
    }


    @DeleteMapping("/restaurants/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public JSendResponse<Object> deleteRestaurant(@PathVariable("restaurant_id") Long id) {
        if (isOwner(id)) {
            throw new IllegalArgumentException("Access denied, you are not the owner of this restaurant");
        }
        restaurantService.deleteRestaurant(id);
        return JSendResponse.success(null);
    }

    private boolean isOwner(Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return restaurant.getOwner().getId().equals(user.getId());
    }
}
