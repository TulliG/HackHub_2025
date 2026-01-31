package it.unicam.cs.hackhub.Patterns.Facade;

import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.DTOs.NotificationDTO;
import it.unicam.cs.hackhub.Application.Services.HackathonService;
import it.unicam.cs.hackhub.Application.Services.NotificationService;
import it.unicam.cs.hackhub.Application.Services.TeamService;
import it.unicam.cs.hackhub.Application.Services.UserService;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
import it.unicam.cs.hackhub.Model.Entities.Notification;
import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;

@Service
public class Facade {

    private final HackathonService hackathonService;
    private final TeamService teamService;
    private final UserService userService;
    private final NotificationService notificationService;

    public Facade(HackathonService hackathonService,
                  TeamService teamService,
                  UserService userService,
                  NotificationService notificationService) {
        this.hackathonService = hackathonService;
        this.teamService = teamService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public void createParticipation() {

    }

    public void sendNotification() {
        //User sender, User receiver, String message
    }

    public void accept(Long id, UserDetails details) {
        Notification notis = notificationService.getById(id);
        switch (notis.getType()) {
            case TEAM_INVITE -> acceptTeamInvite(id, details);
            case JUDGE_INVITE ->  acceptJudgeInvite();
            case MENTOR_INVITE ->  acceptMentorInvite();
            case SUPPORT_REQUEST ->   acceptSupportRequest();
            default -> throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Questa notifica non può essere accettata");
        }
    }

    private void acceptTeamInvite(Long id, UserDetails details) {
        userService.checkIfIsAvailable(details.getUsername());
        Notification notis = notificationService.getById(id);
        if (notis.getSender().getTeam().getHackathon() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Il team sta partecipando ad un hackathon"
            );
        }
        teamService.getById(notis.getTargetId()).addMember(notis.getReceiver());
        notificationService.delete(notis.getId());
        // TODO AGGIUNGI NOTIFICA DI INORMAZIONE
    }

    public void acceptJudgeInvite() {
        //TODO hackathon state check(REGISTRATION)
        //TODO available user check
    }

    public void acceptMentorInvite() {
        //TODO hackathon state check(REGISTRATION, RUNNING)
        //TODO available user check
    }

    public void acceptSupportRequest() {
        //TODO hackathon state check(RUNNING)
    }

    @Transactional
    public NotificationDTO sendTeamInvite(Long id, UserDetails details) {
        if (userService.getByUsername(details.getUsername()).getTeam() == null) {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED,
                    "Lo user non fa parte di un team");
        }
        User sender = userService.getByUsername(details.getUsername());
        return notificationService.send(
                userService.getByUsername(details.getUsername()),
                userService.getById(id),
                "Lo User "+sender.getUsername()+" ti ha invitato al team "+sender.getTeam().getName(),
                NotificationType.TEAM_INVITE,
                sender.getTeam().getId()
                );
    }
}
