package com.vdt.fosho.repository;

import com.vdt.fosho.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
