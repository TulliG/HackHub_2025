package it.unicam.cs.hackhub.Service;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Model.Entity.User;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Model.Enums.Role;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NotificationService {

    private static final Map<Long, Notification> notificationRepo = new HashMap<>();
    private static Long newNotificationId = 1l;

    public void accept(@NonNull Notification notification) {

    }

    public void deny(@NonNull Notification notification) {

    }

    public void sendInfo(@NonNull User sender,@NonNull User receiver,@NonNull String message) {
        createNotification(sender, receiver, message);
    }

    public void sendTeamInvite(@NonNull User sender,@NonNull User receiver) {
        if (sender.getTeam() == null)
            throw new IllegalArgumentException("the sender must be part of a team");
        String message = sender.getUsername() + "invites you to join in his team: " + sender.getTeam().getName() + ".";
        createNotification(sender, receiver, message, NotificationType.TEAM_INVITE, sender.getTeam().getId());
    }

    //TODO refactor with Hackathon getters
    public void sendJudgeInvite(@NonNull User sender,@NonNull User receiver) {
        if (sender.getParticipation() == null || sender.getParticipation().getRole() != Role.ORGANIZER)
            throw new IllegalArgumentException("the sender must be the hackathon's ORGANIZER");
        String message = sender.getUsername() + "invites you to staff as his hackathon's Judge\n  " + /*sender.getParticipation().getHackathon.getName() +*/ ".";
        createNotification(sender, receiver, message, NotificationType.JUDGE_INVITE, sender.getTeam().getId()/*sender.getParticipation().getHackathon.getId()*/);
    }


    //TODO refactor with Hackathon getters
    public void sendMentorInvite(@NonNull User sender,@NonNull User receiver) {
        if (sender.getParticipation() == null || sender.getParticipation().getRole() != Role.ORGANIZER)
            throw new IllegalArgumentException("the sender must be the hackathon's ORGANIZER");
        String message = sender.getUsername() + "invites you to staff as a hackathon's Mentor\n " + /*sender.getParticipation().getHackathon.getName() +*/ ".";
        createNotification(sender, receiver, message, NotificationType.MENTOR_INVITE, sender.getTeam().getId()/*sender.getParticipation().getHackathon.getId()*/);
    }

    //TODO refactor with Hackathon getters
    //TODO implement teamId checks and logic
    public void sendMentorReport(@NonNull User sender,@NonNull User receiver, Long teamId, String comment) {
        if (sender.getParticipation() == null || sender.getParticipation().getRole() != Role.MENTOR)
            throw new IllegalArgumentException("the sender must be a hackathon's MENTOR");
        if (receiver.getParticipation() == null || sender.getParticipation().getRole() != Role.ORGANIZER)
            throw new IllegalArgumentException("the receiver must be the hackathon's ORGANIZER");
        if (!sender.getParticipation().getHackathon().equals(receiver.getParticipation().getHackathon()))
            throw new IllegalArgumentException("the sender and receiver must participate in the same hackathon");
        String message = sender.getUsername() + "has reported this team:\n " + comment + /*sender.getParticipation().getHackathon.getName() +*/ ".";
        createNotification(sender, receiver, message, NotificationType.MENTOR_REPORT, sender.getTeam().getId()/*sender.getParticipation().getHackathon.getId()*/);
    }

    //TODO refactor with Hackathon getters
    public void sendSupportRequest(@NonNull User sender,@NonNull User receiver) {
        if (sender.getParticipation() == null || sender.getParticipation().getRole() != Role.TEAM_MEMBER)
            throw new IllegalArgumentException("the sender must be a hackathon's TEAM MEMBER");
        if (receiver.getParticipation() == null || sender.getParticipation().getRole() != Role.MENTOR)
            throw new IllegalArgumentException("the receiver must be a hackathon's MENTOR");
        if (!sender.getParticipation().getHackathon().equals(receiver.getParticipation().getHackathon()))
            throw new IllegalArgumentException("the sender and receiver must participate in the same hackathon");
        String message = sender.getTeam() + " requested your support.";
        createNotification(sender, receiver, message, NotificationType.SUPPORT_REQUEST, sender.getTeam().getId()/*sender.getParticipation().getHackathon.getId()*/);
    }

    public void createNotification(@NonNull User sender,
                                   @NonNull User receiver,
                                   @NonNull String message,
                                   @NonNull NotificationType type,
                                   @NonNull Long targetId) {
        Notification result = new Notification(sender, receiver, message, type, targetId);
        result.setId(newNotificationId);
        notificationRepo.put(newNotificationId++, result);
    }

    public void createNotification(@NonNull User sender, @NonNull User receiver, @NonNull String message) {
        Notification result = new Notification(sender, receiver, message);
        result.setId(newNotificationId);
        notificationRepo.put(newNotificationId++, result);
    }

    public void deleteNotification(@NonNull Long id) {
        notificationRepo.remove(id);
    }

    public Notification getById(@NonNull Long id) {
        return notificationRepo.get(id);
    }

    public Set<Notification> getAll() {
        return new HashSet<>(notificationRepo.values());
    }

    public Set<Notification> getBySender(@NonNull User sender) {
        return getAll().stream().filter(n -> n.getSender().equals(sender)).collect(Collectors.toSet());
    }

    public Set<Notification> getByReceiver(@NonNull User receiver) {
        return getAll().stream().filter(n -> n.getReceiver().equals(receiver)).collect(Collectors.toSet());
    }

    public Set<Notification> getByType(@NonNull NotificationType type) {
        return getAll().stream().filter(n -> n.getType().equals(type)).collect(Collectors.toSet());
    }
}
