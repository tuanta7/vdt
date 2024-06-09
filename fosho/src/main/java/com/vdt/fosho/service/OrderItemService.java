package com.vdt.fosho.service;

import com.vdt.fosho.dto.OrderItemDTO;
import com.vdt.fosho.entity.Dish;
import com.vdt.fosho.entity.OrderItem;
import com.vdt.fosho.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final DishService dishService;

    // Get all order items in the cart
    public List<OrderItem> getCart(Long userId) {
        return orderItemRepository.findByUserIdAndOrderIdIsNull(userId);
    }


    // Get order items by their ids (for checking out)
    public List<OrderItem> getOrderItemsByIds(List<Long> orderItemIds) {
        return orderItemRepository.findByIdIn(orderItemIds);
    }


    // Add an order item to the cart
    public OrderItem createOrderItem(OrderItemDTO orderItemDTO) {
        Dish dish = dishService.getDishById(orderItemDTO.getDishId());
        orderItemDTO.setDish(dish);

        Optional<OrderItem> existingOrderItem = orderItemRepository.findByUserIdAndDishIdAndOrderIdIsNull(
                orderItemDTO.getUser().getId(),
                orderItemDTO.getDishId());
        if (existingOrderItem.isPresent()) {
            OrderItem orderItem = existingOrderItem.get();
            orderItem.setQuantity(orderItem.getQuantity() + 1);
            return orderItemRepository.save(orderItem);
        }

        OrderItem orderItem = toEntity(orderItemDTO);
        return orderItemRepository.save(orderItem);
    }


    // Save all order items (for creating an order)
    public void saveAll(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }


    // Accepted input fields: quantity, dishId
    public OrderItem toEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrder(orderItemDTO.getOrder());
        orderItem.setDish(orderItemDTO.getDish());
        orderItem.setUser(orderItemDTO.getUser());
        return orderItem;
    }
}
