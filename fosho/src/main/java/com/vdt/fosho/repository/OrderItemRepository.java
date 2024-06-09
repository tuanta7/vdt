package com.vdt.fosho.repository;

import com.vdt.fosho.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

    // Find the order item by user id order id is null (cart items)
    List<OrderItem> findByUserIdAndOrderIdIsNull(Long userId);

    // Find the order item by user id, and order id (items of an order)
    List<OrderItem> findByUserIdAndOrderId(Long userId, Long orderId);

    // Find the order item to place an order
    List<OrderItem> findByIdIn(List<Long> orderItemIds);

    // Check if the order item exists in the cart (to update the quantity)
    Optional<OrderItem> findByUserIdAndDishIdAndOrderIdIsNull(Long id, Long dishId);
}
