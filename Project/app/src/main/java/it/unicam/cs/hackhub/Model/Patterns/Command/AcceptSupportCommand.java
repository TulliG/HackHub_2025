package it.unicam.cs.hackhub.Model.Patterns.Command;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Service.HackathonService;
import it.unicam.cs.hackhub.Service.NotificationService;

/**
 * Class that execute that decline a {@code Notification}.
 */
public class AcceptSupportCommand implements NotificationCommand {

    private final Notification notification;

    public AcceptSupportCommand(Notification notification) {
        if (notification.getType() != NotificationType.SUPPORT_REQUEST)
            throw new IllegalArgumentException("this command is for requesting support only");
        this.notification = notification;
    }

    @Override
    public void execute() {
        new HackathonService().reserveCall(notification.getReceiver(), notification.getSender());
        String message = notification.getReceiver().getUsername() + " has accepted your request.";
        notification.getSender().getTeam()
                .getMembers()
                .forEach(m -> new NotificationService().sendInfo(notification.getReceiver(), m, message));
    }
}
