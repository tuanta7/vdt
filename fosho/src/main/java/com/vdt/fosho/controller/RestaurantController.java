package com.vdt.fosho.controller;

import com.vdt.fosho.dto.CoordinateDTO;
import com.vdt.fosho.dto.RestaurantDTO;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.service.RestaurantService;
import com.vdt.fosho.utils.JSendResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            @Valid @RequestBody CoordinateDTO coordinateDTO
        ) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurantCoordinates(id, coordinateDTO.getLongitude(), coordinateDTO.getLatitude());
        HashMap<String, RestaurantDTO> data = new HashMap<>();
        data.put("restaurant", restaurantService.toDTO(updatedRestaurant));
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
