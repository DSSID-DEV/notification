package com.ead.notification.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationCommand {

    private UUID userId;

    private String title;

    private String message;

}
