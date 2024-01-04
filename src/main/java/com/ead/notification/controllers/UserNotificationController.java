package com.ead.notification.controllers;

import com.ead.notification.dtos.NotificationDTO;
import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    @Autowired
    private NotificationService service;

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getAllNotifications(@PathVariable(value = "userId") UUID userId,
                                                                       @PageableDefault(page = 0, size = 10, sort = "notificationId",
                                                                               direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllNotificationsByUser(userId,  pageable));
    }


    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId, @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDTO notificationDTO) {

        Optional<NotificationModel> notificationModelOptional = service.findByNotificationIdAndUserId(notificationId,  userId);

        if(!notificationModelOptional.isPresent()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
        }
        notificationModelOptional.get().setNotificationStatus(notificationDTO.getNotificationStatus());
        service.saveNotification(notificationModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(notificationModelOptional.get());

    }


}
