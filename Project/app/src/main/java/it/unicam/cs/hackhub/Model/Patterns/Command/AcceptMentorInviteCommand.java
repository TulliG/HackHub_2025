package it.unicam.cs.hackhub.Model.Patterns.Command;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Model.Enums.Role;
import it.unicam.cs.hackhub.Service.HackathonService;
import it.unicam.cs.hackhub.Service.NotificationService;
import it.unicam.cs.hackhub.Service.UserService;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Class that execute a {@code Mentor} invite {@code Notification}.
 */
public class AcceptMentorInviteCommand implements NotificationCommand {

    private final Notification notification;

    public AcceptMentorInviteCommand(@NonNull Notification notification) {
        if (notification.getType() != NotificationType.MENTOR_INVITE)
            throw new IllegalArgumentException("this command is for mentor invitations only");
        this.notification = notification;
    }

    @Override
    public void execute() {
        new HackathonService().getById(notification.getTargetId()).addMentor(notification.getReceiver());
        new UserService().createParticipation(notification.getReceiver().getId(), notification.getTargetId(), Role.MENTOR);
        String message = notification.getReceiver() + "has accepted your invite, now it's one of your hackathon's MENTOR.";
        new NotificationService().createNotification(notification.getReceiver(), notification.getSender(), message);
    }
    
}
