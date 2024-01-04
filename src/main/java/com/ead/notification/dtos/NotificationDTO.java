package com.ead.notification.dtos;

import com.ead.notification.enums.NotificationStatus;
import lombok.Data;

import java.util.UUID;


@Data
public class NotificationDTO {
    private NotificationStatus notificationStatus;
}
