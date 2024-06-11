package com.vdt.fosho.controller;

import com.vdt.fosho.dto.OrderDTO;
import com.vdt.fosho.entity.Order;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.exception.BadRequestException;
import com.vdt.fosho.service.OrderService;
import com.vdt.fosho.utils.JSendResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/restaurants/{restaurant_id}/orders")
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> getOrdersByRestaurantId(
            @PathVariable("restaurant_id") Long restaurantId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int size
    ) {
        Page<Order> orderPage = orderService.getOrdersByRestaurantId(restaurantId, page-1, size);

        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(orderService.toDTO(order));
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("total", orderPage.getTotalPages());
        data.put("orders", orderDTOs);
        return JSendResponse.success(data);
    }

    @PostMapping("/restaurants/{restaurant_id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, OrderDTO>> createOrder(
            @RequestBody  @Valid OrderDTO orderDTO,
            @PathVariable("restaurant_id") Long restaurantId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Order order = orderService.createOrder(orderDTO, restaurantId, user.getId());

        HashMap<String, OrderDTO> data = new HashMap<>();
        data.put("order_item",orderService.toDTO(order));
        return JSendResponse.success(data);
    }

    @GetMapping("/users/{user_id}/orders")
    @ResponseBody
    public JSendResponse<HashMap<String, Object>> getOrdersByUserId(
            @PathVariable("user_id") Long userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "3") int size
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (!user.getId().equals(userId)) {
            throw new BadRequestException("User id does not match");
        }

        Page<Order> orderPage = orderService.getOrdersByUserId(userId, page-1, size);

        List<Order> orders = orderPage.getContent();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(orderService.toDTO(order));
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("total", orderPage.getTotalPages());
        data.put("orders", orderDTOs);
        return JSendResponse.success(data);
    }
}
