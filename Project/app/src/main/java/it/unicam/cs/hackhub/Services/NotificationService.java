package it.unicam.cs.hackhub.Services;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Repositories.NotificationRepository;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class NotificationService {

    NotificationRepository repo = new NotificationRepository();

    public Notification getById(@NonNull Long id) {
        return repo.get(id);
    }

    public void delete(@NonNull Long id) {
        repo.remove(id);
    }

    public List<Notification> getByReceiver(@NonNull Long receiverId) {
        return repo.getAll().stream().filter(n -> n.getReceiver().getId().equals(receiverId)).toList();
    }

    public List<Notification> getByReceiver(@NonNull Long receiverId, @NonNull NotificationType type) {
        return getByReceiver(receiverId).stream().filter(n -> n.getType().equals(type)).toList();
    }
}
