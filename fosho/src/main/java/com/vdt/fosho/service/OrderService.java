package com.vdt.fosho.service;

import com.vdt.fosho.dto.OrderDTO;
import com.vdt.fosho.dto.OrderItemDTO;
import com.vdt.fosho.dto.ShippingAddressDTO;
import com.vdt.fosho.entity.*;
import com.vdt.fosho.exception.BadRequestException;
import com.vdt.fosho.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ShippingAddressService shippingAddressService;
    private final RestaurantService restaurantService;
    private final DishService dishService;


    public Page<Order> getOrdersByUserId(Long userId, String status, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return orderRepository.findAllByUserIdAndStatusOrderByCreatedAtDesc(
                userId,
                OrderStatus.valueOf(status),
                pageable);
    }

    public Page<Order> getOrdersByRestaurantId(Long restaurantId, String status, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return orderRepository.findAllByRestaurantIdAndStatusOrderByCreatedAtAsc(
                restaurantId,
                OrderStatus.valueOf(status),
                pageable);
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO, Long restaurantId, Long userId) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByIds(orderDTO.getItemIds());
        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        ShippingAddress shippingAddress = shippingAddressService.getShippingAddressesByIdAndUserId(
                orderDTO.getShippingAddressId(),
                userId
        );

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrder() != null) {
                throw new BadRequestException("Order item is already in an order");
            }
            if (!orderItem.getUser().getId().equals(userId)) {
                throw new BadRequestException("Order item does not belong to user");
            }
            if (!orderItem.getDish().getRestaurant().getId().equals(restaurantId)) {
                throw new BadRequestException("Order item does not belong to restaurant");
            }
            totalPrice += orderItem.getDish().getPrice() * orderItem.getQuantity();
            totalDiscount += orderItem.getDish().getDiscount() * orderItem.getQuantity();
        }

        Order order = Order.builder().
                createdAt(LocalDateTime.now()).
                status(OrderStatus.PENDING).
                user(orderItems.getFirst().getUser()).
                restaurant(orderItems.getFirst().getDish().getRestaurant()).
                totalPrice(totalPrice).
                totalDiscount(totalDiscount).
                shippingAddress(shippingAddress).
                shippingFee(30000).
                items(orderItems).
                paymentMethod(orderDTO.getPaymentMethod()).
                build();

        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderItemService.saveAll(orderItems);

        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, String status, Long restaurantId){
        Optional<Order> result = orderRepository.findById(orderId);
        if (result.isEmpty()) {
            throw new BadRequestException("Order not found");
        }
        Order order = result.get();
        if (!order.getRestaurant().getId().equals(restaurantId)) {
            throw new BadRequestException("Order does not belong to this restaurant");
        }

        for (OrderItem item : order.getItems()) {
            Dish dish = item.getDish();
            if (!Objects.equals(dish.getRestaurant().getId(), restaurantId)) {
                throw new BadRequestException("Order item does not belong to this restaurant");
            }

            if (dish.getStock() < item.getQuantity()) {
                throw new BadRequestException("Not enough stock for dish: " + dish.getName());
            }

            if (status.equals("CANCELLED")) {
                dish.setStock(dish.getStock() + item.getQuantity());
                dish.setSold(dish.getSold() - item.getQuantity());
                dishService.save(dish);
            }
            else if(status.equals("CONFIRMED")) {
                dish.setStock(dish.getStock() - item.getQuantity());
                dish.setSold(dish.getSold() + item.getQuantity());
                dishService.save(dish);
            }

        }
        order.setStatus(OrderStatus.valueOf(status));
        return orderRepository.save(order);

    }

    public OrderDTO toDTO(Order order) {
        ShippingAddressDTO shippingAddress = shippingAddressService.toDTO(order.getShippingAddress());

        List<OrderItem> items = order.getItems();
        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (OrderItem item : items) {
            orderItems.add(orderItemService.toDTO(item));
        }

        return OrderDTO.builder().
                id(order.getId()).
                totalPrice(order.getTotalPrice()).
                totalDiscount(order.getTotalDiscount()).
                shippingFee(order.getShippingFee()).
                status(String.valueOf(order.getStatus())).
                createdAt(order.getCreatedAt()).
                shippingAddress(shippingAddress).
                items(orderItems).
                restaurant(restaurantService.toDTO(order.getRestaurant())).
                build();
    }
}
