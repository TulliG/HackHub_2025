package it.unicam.cs.hackhub.Patterns.Facade;

import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.DTOs.NotificationDTO;
import it.unicam.cs.hackhub.Application.Mappers.NotificationMapper;
import it.unicam.cs.hackhub.Application.Services.HackathonService;
import it.unicam.cs.hackhub.Application.Services.NotificationService;
import it.unicam.cs.hackhub.Application.Services.TeamService;
import it.unicam.cs.hackhub.Application.Services.UserService;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
import it.unicam.cs.hackhub.Model.Entities.Hackathon;
import it.unicam.cs.hackhub.Model.Entities.HackathonParticipation;
import it.unicam.cs.hackhub.Model.Entities.Notification;
import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Model.Enums.Role;
import it.unicam.cs.hackhub.Model.Enums.State;
import it.unicam.cs.hackhub.Repositories.ParticipationRepository;
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
    private final NotificationMapper notificationMapper;


    public Facade(HackathonService hackathonService,
                  TeamService teamService,
                  UserService userService,
                  NotificationService notificationService,
                  NotificationMapper notificationMapper) {
        this.hackathonService = hackathonService;
        this.teamService = teamService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    public void createParticipation() {

    }

    public void sendNotification() {
        //User sender, User receiver, String message
    }

    public void accept(Long id, String username) {
        Notification notis = notificationService.getById(id);
        switch (notis.getType()) {
            case TEAM_INVITE -> acceptTeamInvite(id, username);
            case JUDGE_INVITE ->  acceptJudgeInvite(id, username);
            case MENTOR_INVITE ->  acceptMentorInvite(id, username);
            case SUPPORT_REQUEST ->   acceptSupportRequest();
            default -> throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Questa notifica non può essere accettata");
        }
    }

    private void acceptTeamInvite(Long id, String username) {
        userService.checkIfIsAvailable(username);
        Notification notis = notificationService.getById(id);
        if (notis.getSender().getTeam().getHackathon() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Il team sta partecipando ad un hackathon"
            );
        }
        teamService.getById(notis.getTargetId()).addMember(notis.getReceiver());
        notificationService.delete(notis.getId());
    }

    public void acceptJudgeInvite(Long id, String username) {
        userService.checkIfIsAvailable(username);

        Notification notis = notificationService.getById(id);
        Hackathon hackathon = hackathonService.get(notis.getTargetId());
        hackathonService.refreshStateIfNeeded(hackathon);
        if (hackathon.getState() != State.REGISTRATION) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Non puoi più accettare la notifica"
            );
        }
        HackathonParticipation participation = new HackathonParticipation(
                notis.getReceiver(),
                hackathon,
                Role.JUDGE);
        hackathon.addParticipation(participation, notis.getReceiver());
        hackathonService.createParticipation(participation);

        notificationService.send(
                notis.getSender(),
                "L'utente "+ notis.getSender().getUsername()+" ha accettato l'invito per essere giudice"
        );
        notificationService.delete(notis.getId());
    }

    public void acceptMentorInvite(Long id, String username) {
        userService.checkIfIsAvailable(username);

        Notification notis = notificationService.getById(id);
        Hackathon hackathon = hackathonService.get(notis.getTargetId());
        hackathonService.refreshStateIfNeeded(hackathon);
        if (hackathon.getState() != State.REGISTRATION && hackathon.getState() != State.RUNNING) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Non puoi più accettare la notifica"
            );
        }
        HackathonParticipation participation = new HackathonParticipation(
                notis.getReceiver(),
                hackathon,
                Role.MENTOR);
        hackathon.addParticipation(participation, notis.getReceiver());
        hackathonService.createParticipation(participation);

        notificationService.send(
                notis.getSender(),
                "L'utente "+ notis.getSender().getUsername()+" ha accettato l'invito per essere mentore"
        );
        notificationService.delete(notis.getId());
    }

    public void acceptSupportRequest() {
        //TODO hackathon state check(RUNNING)
    }

    @Transactional
    public NotificationDTO sendTeamInvite(Long id, String username) {
        if (userService.getByUsername(username).getTeam() == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Lo user non fa parte di un team");
        }
        User sender = userService.getByUsername(username);
        return notificationMapper.toDTO(
                notificationService.send(
                        userService.getByUsername(username),
                        userService.getById(id),
                        "Lo User "+sender.getUsername()+" ti ha invitato al team "+sender.getTeam().getName(),
                        NotificationType.TEAM_INVITE,
                        sender.getTeam().getId()
                )
        );
    }

    public NotificationDTO sendMentorInvite(Long id, String username) {
        User sender = userService.getByUsername(username);
        if (sender.getParticipation() != null && sender.getParticipation().getRole() != Role.ORGANIZER) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Lo user non è un organizzatore"
            );
        }
        Hackathon hackathon = userService.getByUsername(username).getParticipation().getHackathon();
        hackathonService.refreshStateIfNeeded(hackathon);
        if (hackathon.getState() != State.REGISTRATION && hackathon.getState() != State.RUNNING) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "L'hackathon non è più nello stato giusto"
            );
        }

        return notificationMapper.toDTO(
                notificationService.send(
                        sender,
                        userService.getById(id),
                        "Sei stato invitato a diventare mentore nell'hackathon "+hackathon.getName(),
                        NotificationType.MENTOR_INVITE,
                        hackathon.getId()
                )
        );
    }

    public NotificationDTO sendJudgeInvite(Long id, String username) {
        User sender = userService.getByUsername(username);
        if (sender.getParticipation() != null && sender.getParticipation().getRole() != Role.ORGANIZER) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Lo user non è un organizzatore"
            );
        }
        Hackathon hackathon = userService.getByUsername(username).getParticipation().getHackathon();
        hackathonService.refreshStateIfNeeded(hackathon);
        if (hackathon.getState() != State.REGISTRATION) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "L'hackathon non è più nello stato giusto"
            );
        }

        if (hackathon.getParticipations().stream()
                .anyMatch(p -> p.getRole() == Role.JUDGE)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esiste già un giudice per questo hackathon"
            );
        }

        return notificationMapper.toDTO(
                notificationService.send(
                        sender,
                        userService.getById(id),
                        "Sei stato invitato a diventare giudice nell'hackathon "+hackathon.getName(),
                        NotificationType.JUDGE_INVITE,
                        hackathon.getId()
                )
        );
    }
}
