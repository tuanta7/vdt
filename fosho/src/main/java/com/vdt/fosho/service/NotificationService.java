package com.vdt.fosho.service;


import com.vdt.fosho.dto.NotificationDTO;
import com.vdt.fosho.entity.Notification;
import com.vdt.fosho.entity.Restaurant;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.repository.NotificationRepository;
import com.vdt.fosho.repository.RestaurantRepository;
import com.vdt.fosho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .build();
    }

    public List<Notification> findNotificationsByUserId(Long userId){
        return notificationRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    public List<Notification> findNotificationsByRestaurantId(Long restaurantId){
        return notificationRepository.findByRestaurantIdOrderByTimestampDesc(restaurantId);
    }

    public NotificationDTO toDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .seen(notification.isSeen())
                .user(notification.getUser().getUsername())
                .restaurant(notification.getRestaurant().getName())
                .build();
    }

    public List<NotificationDTO> toDTOList(List<Notification> notifications) {
        return notifications.stream().map(this::toDTO).toList();
    }
}
