package com.vdt.fosho.repository;

import com.vdt.fosho.entity.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
