package com.ead.notification.consumers;


import com.ead.notification.dtos.NotificationCommand;
import com.ead.notification.dtos.NotificationDTO;
import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class NotificationConsumer {

    private ModelMapper model;

    @Autowired
    private NotificationService service;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.notificationCommandQueue}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.notificationCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${ead.broken.key.notificationCommandKey}")
    )

    public void listen(@Payload NotificationCommand notificationDTO) {
        var notification = convertToEntity(notificationDTO);
        notification.setCreationDate(LocalDate.now(ZoneId.of("UTC")));
        notification.setNotificationStatus(NotificationStatus.CREATED);
        service.saveNotification(notification);
    }


    private NotificationModel convertToEntity (NotificationCommand notification) {
        model = new ModelMapper();
        return model.map(notification, NotificationModel.class);
    }

    private NotificationCommand convertToDTO (NotificationModel notification) {
        model = new ModelMapper();
        return model.map(notification, NotificationCommand.class);
    }

}
