package it.unicam.cs.hackhub.Model.Patterns.Command;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Service.NotificationService;
import it.unicam.cs.hackhub.Service.TeamService;

/**
 * Class that execute a {@code Team} invite {@code Notification}.
 */
public class AcceptTeamInviteCommand implements NotificationCommand {

    private final TeamService teamService = new TeamService();

    private final Notification notification;

    public AcceptTeamInviteCommand(Notification notification) {
        if (notification.getType() != NotificationType.TEAM_INVITE)
            throw new IllegalArgumentException("this command is for team invitations only");
        this.notification = notification;
    }

    @Override
    public void execute() {
        teamService.addMember(notification.getReceiver(), teamService.getById(notification.getTargetId()));
    }
    
}
