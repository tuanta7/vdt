package com.vdt.fosho.service;


import com.vdt.fosho.dto.NotificationDTO;
import com.vdt.fosho.entity.Notification;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.repository.NotificationRepository;
import com.vdt.fosho.repository.RestaurantRepository;
import com.vdt.fosho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public Notification save(NotificationDTO notificationDTO) {
        Notification notification = toEntity(notificationDTO);
        return notificationRepository.save(notification);
    }

    private Notification toEntity(NotificationDTO notificationDTO) {
        User user = userRepository.findById(notificationDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(notificationDTO.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        return Notification.builder()
                .message(notificationDTO.getMessage())
                .seen(false)
                .fromUserToRestaurant(notificationDTO.isFromUserToRestaurant())
                .user(user)
                .restaurant(restaurant)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public List<Notification> findNotificationsByUserId(Long userId){
        return notificationRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    public Page<Notification> findNotificationsByRestaurantId(Long restaurantId, int page, int size){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return notificationRepository.findByRestaurantIdAndFromUserToRestaurantIsTrueOrderByTimestampDesc(
                restaurantId,
                pageable
        );
    }

    public NotificationDTO toDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .seen(notification.isSeen())
                .user(notification.getUser().getUsername())
                .restaurant(notification.getRestaurant().getName())
                .timestamp(notification.getTimestamp())
                .fromUserToRestaurant(notification.isFromUserToRestaurant())
                .userId(notification.getUser().getId())
                .restaurantId(notification.getRestaurant().getId())
                .build();
    }

    public List<NotificationDTO> toDTOList(List<Notification> notifications) {
        return notifications.stream().map(this::toDTO).toList();
    }
}
