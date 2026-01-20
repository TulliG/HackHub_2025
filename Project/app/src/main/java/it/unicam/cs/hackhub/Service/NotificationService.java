package it.unicam.cs.hackhub.Service;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Model.Entity.Team;
import it.unicam.cs.hackhub.Model.Entity.User;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Model.Enums.Role;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class NotificationService {

    private static final Map<Long, Notification> notificationRepo = new HashMap<>();
    private static Long newNotificationId = 1L;

    public void accept(@NonNull Notification notification) {

        //TODO Implementation
        deleteNotification(notification.getId());
    }

    public void deny(@NonNull Notification notification) {

    }

    public void sendInfo(@NonNull User sender,@NonNull User receiver,@NonNull String message) {
        createNotification(sender, receiver, message);
    }

    public void sendInfo(@NonNull User receiver,@NonNull String message) {
        createNotification(receiver, receiver, message);
    }

    public void sendTeamInvite(@NonNull User sender,@NonNull User receiver) {
        if (sender.getTeam() == null)
            throw new IllegalArgumentException("the sender must be part of a team");
        String message = sender.getUsername() + " invites you to join in his team.\n Team: " + sender.getTeam().getName();
        createNotification(sender, receiver, message, NotificationType.TEAM_INVITE, sender.getTeam().getId());
    }

    public void sendJudgeInvite(@NonNull User sender,@NonNull User receiver) {
        if (sender.getParticipation() == null || sender.getParticipation().getRole() != Role.ORGANIZER)
            throw new IllegalArgumentException("the sender must be the hackathon's ORGANIZER");
        String message = sender.getUsername() + " invites you to staff as his hackathon's Judge.\n Hackathon: " + sender.getParticipation().getHackathon().getName();
        createNotification(sender, receiver, message, NotificationType.JUDGE_INVITE, sender.getParticipation().getHackathon().getId());
    }

    public void sendMentorInvite(@NonNull User sender,@NonNull User receiver) {
        if (sender.getParticipation() == null || sender.getParticipation().getRole() != Role.ORGANIZER)
            throw new IllegalArgumentException("the sender must be the hackathon's ORGANIZER");
        String message = sender.getUsername() + " invites you to staff as a hackathon's Mentor.\n Hackathon: " + sender.getParticipation().getHackathon().getName();
        createNotification(sender, receiver, message, NotificationType.MENTOR_INVITE, sender.getParticipation().getHackathon().getId());
    }

    public void sendMentorReport(@NonNull User sender,@NonNull Team team,@NonNull String comment) {
        if (sender.getParticipation() == null || sender.getParticipation().getRole() != Role.MENTOR)
            throw new IllegalArgumentException("the sender must be a hackathon's MENTOR");;
        if (sender.getParticipation().getHackathon().getTeams().stream().noneMatch(t -> t.equals(team)))
            throw new IllegalArgumentException("the is not present in the hackathon");

        String message = sender.getUsername() + " has reported the team " + team.getName() + ":\n " + comment;
        createNotification(sender, sender.getParticipation().getHackathon().getOrganizer(), message);
    }

    public void sendSupportRequest(@NonNull User sender,@NonNull User receiver) {
        if (!sender.getParticipation().getHackathon().getId().equals(receiver.getParticipation().getHackathon().getId()))
            throw new IllegalArgumentException("the sender and receiver must participate in the same hackathon");
        if (sender.getParticipation() == null || sender.getParticipation().getRole() != Role.TEAM_MEMBER)
            throw new IllegalArgumentException("the sender must be a hackathon's TEAM MEMBER");
        if (receiver.getParticipation() == null || sender.getParticipation().getRole() != Role.MENTOR)
            throw new IllegalArgumentException("the receiver must be a hackathon's MENTOR");
        String message = sender.getTeam().getName() + " requested your support.";
        createNotification(sender, receiver, message, NotificationType.SUPPORT_REQUEST, sender.getParticipation().getHackathon().getId());
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
