package com.ead.notification.services.impl;

import com.ead.notification.dtos.NotificationCommand;
import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRepository;
import com.ead.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository repository;
    @Override
    public NotificationModel saveNotification(NotificationModel notification) {
           return repository.save(notification);
    }

    @Override
    public Page<NotificationModel> findAllNotificationsByUser(UUID userId, Pageable pageable) {
        return repository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable);
    }

    @Override
    public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return repository.findByNotificationIdAndUserId(notificationId, userId);
    }
}
