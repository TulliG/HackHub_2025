package it.unicam.cs.hackhub.Application.Mappers;

import it.unicam.cs.hackhub.Application.DTOs.NotificationDTO;
import it.unicam.cs.hackhub.Model.Entities.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getSender() == null
                        ? "System"
                        : notification.getSender().getUsername(),
                notification.getReceiver().getUsername(),
                notification.getMessage()
        );
    }
}
