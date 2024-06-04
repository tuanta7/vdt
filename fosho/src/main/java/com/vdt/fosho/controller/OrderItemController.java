package com.vdt.fosho.controller;

import com.vdt.fosho.dto.OrderItemDTO;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.entity.OrderItem;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.service.DishService;
import com.vdt.fosho.service.OrderItemService;
import com.vdt.fosho.utils.JSendResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderItemController {

    OrderItemService orderItemService;
    DishService dishService;

    @GetMapping("/carts")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, List<OrderItemDTO>>> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<OrderItem> orderItems = orderItemService.getCart(user.getId());
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemDTOs.add(orderItemService.toDTO(orderItem));
        }

        HashMap<String, List<OrderItemDTO>> data = new HashMap<>();
        data.put("order_items", orderItemDTOs);
        return JSendResponse.success(data);
    }

    @PostMapping("/carts")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, OrderItemDTO>> addOrderItem(
            @RequestBody OrderItemDTO orderItemDTO
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Dish dish = dishService.getDishById(orderItemDTO.getDishId());

        orderItemDTO.setUser(user);
        orderItemDTO.setDish(dish);
        OrderItem newOrderItem = orderItemService.createOrderItem(orderItemDTO.toOrderItem());

        HashMap<String, OrderItemDTO> data = new HashMap<>();
        data.put("order_item", orderItemService.toDTO(newOrderItem));
        return JSendResponse.success(data);
    }
}
