package it.unicam.cs.hackhub.Model.Patterns.Command;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Service.HackathonService;
import it.unicam.cs.hackhub.Service.NotificationService;

/**
 * Class that execute that decline a {@code Notification}.
 */
public class AcceptSupportCommand implements NotificationCommand {

    private final NotificationService notificationService = new NotificationService();
    private final Notification notification;

    public AcceptSupportCommand(Notification notification) {
        this.notification = notification;
    }

    @Override
    public void execute() {
        new HackathonService().reserveCall(notification.getReceiver(), notification.getSender());
        String message = notification.getReceiver().getUsername() + " has accepted your request.";
        notification.getSender().getTeam()
                .getMembers()
                .forEach(m -> notificationService.sendInfo(notification.getReceiver(), m, message));
        notificationService.deleteNotification(notification.getId());
    }
}
