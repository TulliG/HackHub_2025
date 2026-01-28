package it.unicam.cs.hackhub.Services;

import it.unicam.cs.hackhub.Model.Entities.Notification;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Repositories.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public Notification getById(@NonNull Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with id " + id));
    }

    public void delete(@NonNull Long id) {
        notificationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Notification> getByReceiver(@NonNull Long receiverId) {
        return notificationRepository.findByReceiverId(receiverId);
    }

    @Transactional(readOnly = true)
    public List<Notification> getByReceiver(@NonNull Long receiverId, @NonNull NotificationType type) {
        return notificationRepository.findByReceiverIdAndType(receiverId, type);
    }
}
