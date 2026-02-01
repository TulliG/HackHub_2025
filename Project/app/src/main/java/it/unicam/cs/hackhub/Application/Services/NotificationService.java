package it.unicam.cs.hackhub.Application.Services;

import it.unicam.cs.hackhub.Model.Entities.Notification;
import it.unicam.cs.hackhub.Model.Entities.User;
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
    public List<Notification> getByReceiver(@NonNull String username) {
        return notificationRepository
                .findByReceiverId(userService.getByUsername(username).getId());
    }

    public List<Notification> getInvites(@NonNull String username) {
        return getByReceiver(username)
                .stream()
                .filter(n ->
                        n.getType() == NotificationType.TEAM_INVITE ||
                                n.getType() == NotificationType.MENTOR_INVITE ||
                                n.getType() == NotificationType.JUDGE_INVITE
                )
                .toList();
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

    public List<Notification> getByTypeAndTargetId(String username, NotificationType type, Long targetId) {
        return notificationRepository
                .findByReceiverUsernameAndTypeAndTargetId(username, type, targetId);
    }
}
