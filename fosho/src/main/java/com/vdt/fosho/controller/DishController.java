package com.vdt.fosho.controller;

import com.vdt.fosho.dto.DishDTO;
import com.vdt.fosho.elasticsearch.document.DishDocument;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.exception.BadRequestException;
import com.vdt.fosho.exception.ForbiddenException;
import com.vdt.fosho.service.CloudinaryService;
import com.vdt.fosho.service.DishService;
import com.vdt.fosho.service.RestaurantService;
import com.vdt.fosho.utils.JSendResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@RestController
@AllArgsConstructor
public class DishController {

    RestaurantService restaurantService;
    DishService dishService;
    CloudinaryService cloudinaryService;

    @GetMapping("/dishes")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> getAllDishes(
            @RequestParam(required = false,defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        if (limit < 1 || page < 1) {
            throw new BadRequestException("Invalid limit or page");
        }
        Page<DishDocument> dishesPage = dishService.getAllDishes(q, page-1, limit);
        List<DishDocument> dishes = dishesPage.getContent();

        HashMap<String, Object> data = new HashMap<>();
        data.put("dishes", dishes);
        data.put("total", dishesPage.getTotalPages());
        return JSendResponse.success(data);
    }

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
        verifyOwner(restaurantId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        dishDTO.setRestaurant(restaurant);

        Dish createdDish = dishService.createDish(dishDTO);
        HashMap<String, DishDTO> data = new HashMap<>();
        data.put("dish", dishService.toDTO(createdDish));
        return JSendResponse.success(data);
    }



    @PatchMapping("/restaurants/{restaurant_id}/dishes/{dish_id}/thumbnail")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, DishDTO>> updateDishThumbnail(
            @PathVariable("restaurant_id") Long restaurantId,
            @PathVariable("dish_id") Long dishId,
            @RequestParam MultipartFile image
    ) throws IOException {
        verifyOwner(restaurantId);

        BufferedImage bi = ImageIO.read(image.getInputStream());
        if (bi == null) {
            throw new BadRequestException("Invalid image file");
        }
        Map result = cloudinaryService.upload(image);

        String imageIdToDelete = dishService.getDishById(dishId).getThumbnailPublicId();
        Dish updatedDish = dishService.updateDishThumbnail(
                dishId,
                result.get("url").toString(),
                result.get("public_id").toString()
        );
        if (imageIdToDelete != null) {
            cloudinaryService.delete(imageIdToDelete);
        }

        HashMap<String, DishDTO> data = new HashMap<>();
        data.put("dish", dishService.toDTO(updatedDish));
        return JSendResponse.success(data);
    }

    private void verifyOwner(Long restaurantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Long ownerId = restaurantService.getRestaurantOwnerIdById(restaurantId);
        if (!ownerId.equals(user.getId())) {
            throw new ForbiddenException("This user is not the owner of this restaurant");
        }
    }
}
