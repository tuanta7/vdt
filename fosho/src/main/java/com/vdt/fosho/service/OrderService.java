package com.vdt.fosho.service;

import com.vdt.fosho.dto.OrderDTO;
import com.vdt.fosho.entity.Order;
import com.vdt.fosho.entity.OrderItem;
import com.vdt.fosho.entity.OrderStatus;
import com.vdt.fosho.exception.BadRequestException;
import com.vdt.fosho.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;

    @Transactional
    public Order createOrder(List<Long> orderItemIds, Long restaurantId, Long userId) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByIds(orderItemIds);
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrder().getId() != null) {
                throw new BadRequestException("Order item is already in an order");
            }
            if (!orderItem.getUser().getId().equals(userId)) {
                throw new BadRequestException("Order item does not belong to user");
            }
            if (!orderItem.getDish().getRestaurant().getId().equals(restaurantId)) {
                throw new BadRequestException("Order item does not belong to restaurant");
            }
        }
        Order order = Order.builder().
                createdAt(LocalDateTime.now()).
                status(OrderStatus.PENDING).
                user(orderItems.getFirst().getUser()).
                restaurant(orderItems.getFirst().getDish().getRestaurant()).
                build();


        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderItemService.saveAll(orderItems);
        return orderRepository.save(order);
    }

    public OrderDTO toDTO(Order order) {
        return OrderDTO.builder().
                items(order.getItems()).
                build();
    }
}
