package com.vdt.fosho.service;

import com.vdt.fosho.entity.OrderItem;
import com.vdt.fosho.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> getCart(Long userId) {
        return orderItemRepository.findByUserIdAndOrderIdIsNull(userId);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
