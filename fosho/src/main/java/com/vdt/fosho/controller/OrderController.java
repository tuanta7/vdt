package com.vdt.fosho.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vdt.fosho.dto.OrderDTO;
import com.vdt.fosho.entity.Order;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.exception.BadRequestException;
import com.vdt.fosho.service.OrderService;
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
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/restaurants/{restaurant_id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public JSendResponse<HashMap<String, OrderDTO>> createOrder(
            @RequestBody  List<Long> orderItemIds,
            @PathVariable("restaurant_id") Long restaurantId
    ) {
        if (orderItemIds.isEmpty()) {
            throw new  BadRequestException("Order items cannot be empty");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Order order = orderService.createOrder(
                orderItemIds,
                restaurantId,
                user.getId()
        );

        HashMap<String, OrderDTO> data = new HashMap<>();
        data.put("order_item",orderService.toDTO(order));
        return JSendResponse.success(data);
    }
}
