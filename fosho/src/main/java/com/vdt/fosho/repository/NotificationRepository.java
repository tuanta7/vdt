package com.vdt.fosho.repository;

import com.vdt.fosho.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRestaurantIdOrderByTimestampDesc(Long restaurantId);

    List<Notification> findByUserIdOrderByTimestampDesc(Long userId);
}
