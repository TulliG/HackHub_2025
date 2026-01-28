package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entities.Notification;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByReceiverId(Long receiverId);

    List<Notification> findByReceiverIdAndType(Long receiverId, NotificationType type);
}
