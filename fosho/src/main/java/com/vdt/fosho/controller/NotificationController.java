package com.vdt.fosho.controller;

import com.vdt.fosho.dto.NotificationDTO;
import com.vdt.fosho.entity.Notification;
import com.vdt.fosho.entity.User;
import com.vdt.fosho.exception.ForbiddenException;
import com.vdt.fosho.service.NotificationService;
import com.vdt.fosho.service.RestaurantService;
import com.vdt.fosho.utils.JSendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final RestaurantService restaurantService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notifications")
    public void processNotification(@Payload NotificationDTO notificationDTO) {
        Notification savedNotification = notificationService.save(notificationDTO);

        // when user's orders get updated
        String receiver = savedNotification.getUser().getUsername();
        // when users place an order
        if(savedNotification.isFromUserToRestaurant()){
            receiver = savedNotification.getRestaurant().getId().toString();
        }
        // anhtuan9702@gmai.com/queue/notifications or 1/queue/notifications
        simpMessagingTemplate.convertAndSendToUser(receiver, "/queue/notifications", notificationDTO);
    }

    @GetMapping("/notifications")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, List<NotificationDTO>>> getUserNotification(
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Notification> notifications = notificationService.findNotificationsByUserId(user.getId());

        HashMap<String, List<NotificationDTO>> data = new HashMap<>();
        data.put("notifications", notificationService.toDTOList(notifications));

        return JSendResponse.success(data);
    }

    @GetMapping("/restaurants/{restaurant_id}/notifications")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSendResponse<HashMap<String, List<NotificationDTO>>> getRestaurantNotification(
            @PathVariable("restaurant_id") Long restaurantId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Long ownerId = restaurantService.getRestaurantOwnerIdById(restaurantId);
        if (!user.getId().equals(ownerId)) {
            throw new ForbiddenException("You are not allowed to access this resource");
        }

        List<Notification> notifications = notificationService.findNotificationsByRestaurantId(restaurantId);
        HashMap<String, List<NotificationDTO>> data = new HashMap<>();

        data.put("notifications", notificationService.toDTOList(notifications));
        return JSendResponse.success(data);
    }
}
