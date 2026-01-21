package it.unicam.cs.hackhub.Model.Patterns.Command;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Model.Enums.Role;
import it.unicam.cs.hackhub.Service.HackathonService;
import it.unicam.cs.hackhub.Service.NotificationService;
import it.unicam.cs.hackhub.Service.UserService;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Class that execute a {@code Judge} invite {@code Notification}.
 */
public class AcceptJudgeInviteCommand implements NotificationCommand {

    private final NotificationService notificationService = new NotificationService();
    private final UserService userService = new UserService();

    private final Notification notification;

    public AcceptJudgeInviteCommand(@NonNull Notification notification) {
        if (notification.getType() != NotificationType.JUDGE_INVITE)
            throw new IllegalArgumentException("this command is for judge invitations only");
        this.notification = notification;
    }

    @Override
    public void execute() {
        new HackathonService().getById(notification.getTargetId()).addJudge(notification.getReceiver());
        userService.createParticipation(notification.getReceiver().getId(), notification.getTargetId(), Role.JUDGE);
        String message = notification.getReceiver() + "has accepted your invite, now it's your hackathon's JUDGE.";
        notificationService.createNotification(notification.getReceiver(), notification.getSender(), message);
        notificationService.deleteNotification(notification.getId());
    }
    
}
