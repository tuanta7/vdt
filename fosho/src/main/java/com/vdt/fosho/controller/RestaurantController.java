package com.vdt.fosho.controller;

import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.service.RestaurantService;
import com.vdt.fosho.utils.JSendResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
//@RequestMapping(path = "/users/{user_id}")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, List<Restaurant>>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        HashMap<String, List<Restaurant>> data = new HashMap<>();
        data.put("restaurants", restaurants);
        return JSendResponse.success(data);
    }


    @PostMapping("/restaurants")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, Restaurant>> createRestaurant(
            @Valid @RequestBody RestaurantDTO restaurantDTO
    ) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurantDTO.toRestaurant());
        HashMap<String, Restaurant> data = new HashMap<>();
        data.put("restaurant", createdRestaurant);
        return JSendResponse.success(data);
    }


    @GetMapping("/restaurants/{restaurant_id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Restaurant>> getRestaurantById(@PathVariable("restaurant_id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        HashMap<String, Restaurant> data = new HashMap<>();
        data.put("restaurant", restaurant);
        return JSendResponse.success(data);
    }


    @PutMapping("/restaurants/{restaurant_id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Restaurant>> updateRestaurant(
            @PathVariable("restaurant_id") Long id,
            @Valid @RequestBody RestaurantDTO restaurantDTO
    ) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDTO.toRestaurant());
        HashMap<String, Restaurant> data = new HashMap<>();
        data.put("restaurant", updatedRestaurant);
        return JSendResponse.success(data);
    }


    @DeleteMapping("/restaurants/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public JSendResponse<Object> deleteRestaurant(@PathVariable("restaurant_id") Long id) {
        restaurantService.deleteRestaurant(id);
        return JSendResponse.success(null);
    }
}
