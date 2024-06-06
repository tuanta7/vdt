package com.vdt.fosho.repository;

import com.vdt.fosho.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

    // Find the order item by user id order id is null (cart items)
    List<OrderItem> findByUserIdAndOrderIdIsNull(Long userId);

    // Find the order item by user id, and order id (items of an order)
    List<OrderItem> findByUserIdAndOrderId(Long userId, Long orderId);
}
