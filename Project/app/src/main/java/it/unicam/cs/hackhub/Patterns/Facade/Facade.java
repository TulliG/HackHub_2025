package it.unicam.cs.hackhub.Patterns.Facade;

import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.DTOs.NotificationDTO;
import it.unicam.cs.hackhub.Application.Mappers.NotificationMapper;
import it.unicam.cs.hackhub.Application.Services.HackathonService;
import it.unicam.cs.hackhub.Application.Services.NotificationService;
import it.unicam.cs.hackhub.Application.Services.TeamService;
import it.unicam.cs.hackhub.Application.Services.UserService;
import it.unicam.cs.hackhub.Controllers.Requests.AcceptSupportRequest;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
import it.unicam.cs.hackhub.Model.Entities.*;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Model.Enums.Role;
import it.unicam.cs.hackhub.Model.Enums.State;
import it.unicam.cs.hackhub.Repositories.ParticipationRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.List;

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

    public void accept(Long id, String username, AcceptSupportRequest body) {
        Notification notis = notificationService.getById(id);

        switch (notis.getType()) {
            case TEAM_INVITE -> acceptTeamInvite(id, username);
            case JUDGE_INVITE -> acceptJudgeInvite(id, username);
            case MENTOR_INVITE -> acceptMentorInvite(id, username);
            case SUPPORT_REQUEST -> {
                String description = (body != null) ? body.description() : null;
                if (description == null || description.isBlank()) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "La descrizione dell'appuntamento è obbligatoria per una SUPPORT_REQUEST"
                    );
                }
                acceptSupportRequest(id, username, description);
            }
            default -> throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Questa notifica non può essere accettata"
            );
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

    public void acceptSupportRequest(Long id, String username, String description) {
        User user = userService.getByUsername(username);

        if (user.getParticipation() == null || user.getParticipation().getRole() != Role.MENTOR) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }

        Notification n = notificationService.getById(id);

        if (n.getType() != NotificationType.SUPPORT_REQUEST) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Notifica non valida");
        }
        if (!n.getReceiver().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Questa notifica non è tua");
        }

        Hackathon h = user.getParticipation().getHackathon();
        hackathonService.refreshStateIfNeeded(h);

        if (h.getState() != State.RUNNING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }

        Team team = n.getSender().getTeam();
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Il team della richiesta non esiste");
        }

        List<Appointment> calendar = hackathonService.getAppointments(user.getId(), h.getId());

        long halfHours = Duration.between(h.getStartDate(), h.getEvaluationDate()).toMinutes() / 30;

        if (calendar.size() >= halfHours) {
            for (User u : team.getMembers()) {
                notificationService.send(u, "Il mentore non può accettare: calendario pieno");
            }
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Il calendario è pieno");
        }

        Appointment app = new Appointment(h, user, team, description);
        hackathonService.addAppointment(app, h);

        for (User u : team.getMembers()) {
            notificationService.send(u, description);
        }
        notificationService.delete(id);
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

    public NotificationDTO sendSupportRequest(Long id, String username) {
        User user = userService.getByUsername(username);

        if (user.getTeam() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non sei in un team");
        }
        if (user.getParticipation() == null || user.getParticipation().getRole() != Role.TEAM_MEMBER) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }

        Hackathon h = user.getParticipation().getHackathon();
        hackathonService.refreshStateIfNeeded(h);

        if (h.getState() != State.RUNNING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }

        Team team = user.getTeam();

        if (team.getHackathon() == null || !team.getHackathon().getId().equals(h.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Il tuo team non è iscritto a questo hackathon");
        }

        return notificationMapper.toDTO(
                notificationService.send(
                        user,
                        userService.getById(id),
                        "Il team "+user.getTeam().getName()+" ha inviato una richiesta di supporto",
                        NotificationType.SUPPORT_REQUEST,
                        h.getId()
                )
        );
    }

    public NotificationDTO sendReport(Long teamId, String username, String report) {
        User user = userService.getByUsername(username);
        if (user.getParticipation() == null || user.getParticipation().getRole() != Role.MENTOR) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }
        Hackathon h = user.getParticipation().getHackathon();
        hackathonService.refreshStateIfNeeded(h);
        if (h.getState() != State.RUNNING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }


        Team team = teamService.getById(teamId);
        return notificationMapper.toDTO(
                notificationService.send(
                    user,
                    hackathonService.getOrganizer(h),
                    "Team " +team.getName() + " segnalato: "+report,
                    NotificationType.REPORT,
                    teamId
            )
        );
    }

    public List<Notification> getSupportRequests(@NonNull String username) {
        User user = userService.getByUsername(username);
        if (user.getParticipation() == null || user.getParticipation().getRole() != Role.MENTOR) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }

        Hackathon h = user.getParticipation().getHackathon();
        hackathonService.refreshStateIfNeeded(h);

        if (h.getState() != State.RUNNING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }

        return notificationService.getByType(username, NotificationType.SUPPORT_REQUEST);
    }
}
