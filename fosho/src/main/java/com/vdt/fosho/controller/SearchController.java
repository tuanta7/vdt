package com.vdt.fosho.controller;

import com.vdt.fosho.dto.DishDTO;
import com.vdt.fosho.elasticsearch.document.DishDocument;
import com.vdt.fosho.elasticsearch.document.RestaurantDocument;
import com.vdt.fosho.service.DishService;
import com.vdt.fosho.service.RestaurantService;
import com.vdt.fosho.utils.JSendResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
public class SearchController {

    private final RestaurantService restaurantService;
    private final DishService dishService;
    
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> search(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Page<DishDocument> dishes = dishService.getAllDishes(q, 0, limit);
        Page<RestaurantDocument> restaurants = restaurantService.getAllRestaurants(q, 0, 3);

        HashMap<String, Object> data = new HashMap<>();
        data.put("dishes", dishes.getContent());
        data.put("restaurants", restaurants.getContent());
        return JSendResponse.success(data);
    }
}
