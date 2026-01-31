package it.unicam.cs.hackhub.Application.Services;

import it.unicam.cs.hackhub.Application.DTOs.NotificationDTO;
import it.unicam.cs.hackhub.Application.Mappers.NotificationMapper;
import it.unicam.cs.hackhub.Model.Entities.Notification;
import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Repositories.NotificationRepository;
import it.unicam.cs.hackhub.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;


    public NotificationService(NotificationRepository notificationRepository,
                               UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Notification getById(@NonNull Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notifica con id " + id + " non trovata"));
    }

    public void delete(@NonNull Long id) {
        notificationRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Notification> getByReceiver(@NonNull UserDetails details) {
        return notificationRepository
                .findByReceiverId(userService.getByUsername(details.getUsername()).getId());
    }

    @Transactional(readOnly = true)
    public List<Notification> getByReceiver(@NonNull UserDetails details, @NonNull NotificationType type) {
        return notificationRepository
                .findByReceiverIdAndType(userService.getByUsername(details.getUsername()).getId(), type);
    }

    @Transactional(readOnly = true)
    public Notification send(@NonNull User sender, @NonNull User receiver,
                                @NonNull String messagge,@NonNull NotificationType type, @NonNull Long id) {
        Notification notis = new Notification(sender, receiver, messagge, type, id);
        notificationRepository.save(notis);
        return notis;
    }

    public Notification send(@NonNull User receiver,
                             @NonNull String messagge) {
        Notification notis = new Notification(receiver, messagge);
        notificationRepository.save(notis);
        return notis;
    }
}
