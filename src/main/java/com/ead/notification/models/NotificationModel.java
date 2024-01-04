package com.ead.notification.models;


import com.ead.notification.enums.NotificationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_NOTIFICATIONS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationModel implements Serializable {

    private static final long serialVerionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID notificationId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDate creationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;


}
