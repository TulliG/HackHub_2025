package it.unicam.cs.hackhub.Application.Services;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import it.unicam.cs.hackhub.Application.Exceptions.HackathonCancelledException;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
import it.unicam.cs.hackhub.Controllers.Requests.SubmissionRequest;
import it.unicam.cs.hackhub.Model.Entities.*;
import it.unicam.cs.hackhub.Model.Enums.Role;
import it.unicam.cs.hackhub.Model.Enums.State;
import it.unicam.cs.hackhub.Patterns.Builder.Builder;
import it.unicam.cs.hackhub.Patterns.Builder.HackathonBuilder;
import it.unicam.cs.hackhub.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for managing {@code Hackathon}s and {@code Submission}s
 */
@Service
@Transactional
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final ConcludedHackathonRepository concludedHackathonRepository;
    private final UserService userService;
    private final AppointmentRepository appointmentRepository;
    private final SubmissionRepository submissionRepository;
    private final Clock clock;
    private final TeamRepository teamRepository;
    private final ParticipationRepository participationRepository;
    private final HackathonCancellationService cancellationService;
    private final NotificationService notificationService;

    public HackathonService(HackathonRepository hackathonRepository,
                            UserService userService,
                            AppointmentRepository appointmentRepository,
                            ConcludedHackathonRepository concludedHackathonRepository,
                            Clock clock,
                            TeamRepository teamRepository,
                            ParticipationRepository participationRepository,
                            SubmissionRepository submissionRepository,
                            HackathonCancellationService cancellationService,
                            NotificationService notificationService) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
        this.appointmentRepository = appointmentRepository;
        this.concludedHackathonRepository = concludedHackathonRepository;
        this.clock = clock;
        this.teamRepository = teamRepository;
        this.participationRepository = participationRepository;
        this.submissionRepository = submissionRepository;
        this.cancellationService = cancellationService;
        this.notificationService = notificationService;
    }


    @Transactional(readOnly = true)
    public Hackathon get(@NonNull Long id) {
        return hackathonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hackathon not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<Hackathon> getAll() {
        return hackathonRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ConcludedHackathon> getAllConcluded() {
        return concludedHackathonRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Submission> getSubmissions(@NonNull String username
    ) {
        User user = userService.getByUsername(username);
        if (user.getParticipation() == null || user.getParticipation().getRole() == Role.TEAM_MEMBER) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }

        Hackathon h = user.getParticipation().getHackathon();
        refreshStateIfNeeded(h);

        if (h.getState() == State.REGISTRATION) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }

        return h.getSubmissions().stream().toList();
    }

    public Submission getSubmission(Long id, String username) {
        User user = userService.getByUsername(username);
        if (user.getParticipation() == null || user.getParticipation().getRole() == Role.TEAM_MEMBER) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }

        Hackathon h = user.getParticipation().getHackathon();
        refreshStateIfNeeded(h);

        if (h.getState() == State.REGISTRATION) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }

        return submissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sottomissione non trovata"));
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAppointments(@NonNull Long mentorId, @NonNull Long hackathonId) {
        return appointmentRepository.findByMentorIdAndHackathonId(mentorId, hackathonId);
    }

    public Hackathon createHackathon(
            CreateHackathonRequest req,
            UserDetails details) {

        User organizer = userService.checkIfIsAvailable(details.getUsername());

        if (!req.startDate().isAfter(LocalDateTime.now(clock)) ||
                !req.startDate().isBefore(req.evaluationDate()) ||
                !req.evaluationDate().isBefore(req.endingDate())
        ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dates are invalid"
            );
        }

        Builder builder = new HackathonBuilder();
        builder.setName(req.name());
        builder.setLocation(req.location());
        builder.setRules(req.rules());
        builder.setPrize(req.prize());
        builder.setCreationDate(LocalDateTime.now(clock));
        builder.setStartDate(req.startDate());
        builder.setEvaluationDate(req.evaluationDate());
        builder.setEndingDate(req.endingDate());
        Hackathon h = builder.getResult();

        hackathonRepository.save(h);

        HackathonParticipation participation = new HackathonParticipation(organizer, h, Role.ORGANIZER);
        createParticipation(participation);

        h.addParticipation(participation, organizer);
        return h;
    }

    public void createParticipation(HackathonParticipation participation) {
        hackathonRepository.save(participation.getHackathon());
    }

    public List<Hackathon> getByState(State state) {
        return hackathonRepository.findAll()
                .stream()
                .filter(h -> h.getState() == state)
                .toList();
    }

    public List<User> getMentors(String username) {
        User user = userService.getByUsername(username);

        if (user.getTeam() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non sei in un team");
        }
        if (user.getParticipation() == null || user.getParticipation().getRole() != Role.TEAM_MEMBER) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }

        Hackathon h = user.getParticipation().getHackathon();
        refreshStateIfNeeded(h);

        if (h.getState() != State.RUNNING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }

        Team team = user.getTeam();

        if (team.getHackathon() == null || !team.getHackathon().getId().equals(h.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Il tuo team non è iscritto a questo hackathon");
        }
         return h.getParticipations()
                 .stream()
                 .filter(p -> p.getRole() == Role.MENTOR)
                 .map(HackathonParticipation::getUser)
                 .toList();
    }

    @Transactional
    public void registerTeam(Long hackathonId, String username) {

        User user = userService.getByUsername(username);
        Team team = user.getTeam();

        if (team == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non fai parte di un team");
        }

        if (team.getHackathon() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Il team è già iscritto ad un hackathon");
        }

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Hackathon non trovato con id " + hackathonId
                ));

        refreshStateIfNeeded(hackathon);

        if (hackathon.getState() != State.REGISTRATION) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "L'hackathon non ha le iscrizioni aperte");
        }

        hackathon.addTeam(team);
        for (User member : team.getMembers()) {
            HackathonParticipation hp = new HackathonParticipation(member, hackathon, Role.TEAM_MEMBER);
            participationRepository.save(hp);
            member.setParticipation(hp);
        }
    }

    @Transactional
    public void unregisterTeam(String username) {
        User user = userService.getByUsername(username);
        Team team = user.getTeam();
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non sei in un team");
        }
        Hackathon hackathon = team.getHackathon();
        if (hackathon == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non sei iscritto ad un hackathon");
        }
        refreshStateIfNeeded(hackathon);
        if (hackathon.getState() != State.REGISTRATION) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi più disiscriveri all'hackathon");
        }

        hackathon.removeTeam(team);
        for (User member : team.getMembers()) {
            HackathonParticipation p = member.getParticipation();
            if (p == null) continue;
            hackathon.removeParticipation(p, member);
            participationRepository.delete(p);
        }
    }

    @Transactional
    public Submission uploadSubmission(SubmissionRequest submissionRequest, String username) {
        User user = userService.getByUsername(username);

        if (user.getTeam() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non sei in un team");
        }
        if (user.getParticipation() == null || user.getParticipation().getRole() != Role.TEAM_MEMBER) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }

        Hackathon h = user.getParticipation().getHackathon();
        refreshStateIfNeeded(h);

        if (h.getState() != State.RUNNING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi fare un upload in questo stato");
        }

        Team team = user.getTeam();

        if (team.getHackathon() == null || !team.getHackathon().getId().equals(h.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Il tuo team non è iscritto a questo hackathon");
        }

        // logica di sovrascrizione della sottomissione
        Submission s = submissionRepository
                .findByHackathonIdAndTeamId(h.getId(), team.getId())
                .map(existing -> {
                    existing.setContent(submissionRequest.content());
                    return existing;
                })
                .orElseGet(() -> {
                    Submission created = new Submission(submissionRequest.content(), team, h);
                    h.addSubmission(created);
                    return created;
                });

        return submissionRepository.save(s);
    }

    public void addAppointment(Appointment appointment, Hackathon hackathon) {
        appointmentRepository.save(appointment);
        hackathon.addAppointment(appointment);
    }

    public List<Appointment> getCalender(String username) {
        User user = userService.getByUsername(username);

        if (user.getParticipation() == null || user.getParticipation().getRole() != Role.MENTOR) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }
        Hackathon h = user.getParticipation().getHackathon();
        refreshStateIfNeeded(h);

        if (h.getState() != State.RUNNING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }

        return getAppointments(user.getId(),  h.getId());
    }

    public List<Team> getTeams(String username) {
        User user = userService.getByUsername(username);
        if (user.getParticipation() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non stai partecipando ad un hackathon");
        }

        Hackathon h = user.getParticipation().getHackathon();
        return h.getTeams().stream().toList();
    }



    public void rateSubmission(Long id, String username, int grade) {
        Submission s = getSubmission(id, username);
        s.setGrade(grade);
        submissionRepository.save(s);
    }

    public void proclaimWinner(Long id, String username) {
        User user = userService.getByUsername(username);

        if (user.getParticipation() == null || user.getParticipation().getRole() != Role.ORGANIZER) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non hai la partecipazione adatta");
        }
        Hackathon h = user.getParticipation().getHackathon();
        refreshStateIfNeeded(h);

        if (h.getState() != State.CONCLUDED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Non puoi farlo in questo stato");
        }

        Team t = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Il team non esiste"
                ));

        double userPrize = (double) h.getPrize() / t.getMembers().size();

        for (User u : t.getMembers()) {
            u.setWallet(u.getWallet() + userPrize);
            notificationService.send(u, "Hai vinto l'hackathon: "+h.getName());
        }

        cancellationService.cancelHackathon(h.getId(), t.getName());
    }

    public void refreshStateIfNeeded(Hackathon h) {
        LocalDateTime now = LocalDateTime.now(clock);
        State computed = h.computeState(now);

        if (computed == State.RUNNING) {
            long teamCount = teamRepository.countByHackathonId(h.getId());
            long judgeCount = participationRepository.countByHackathonIdAndRole(h.getId(), Role.JUDGE);
            long mentorCount = participationRepository.countByHackathonIdAndRole(h.getId(), Role.MENTOR);
            if (judgeCount < 1 || mentorCount < 1 || teamCount < 2) {
                cancellationService.cancelHackathon(h.getId(), "requisiti non soddisfatti");
                throw new HackathonCancelledException("Hackathon cancellato: requisiti non soddisfatti");
            }
        }

        if (computed != h.getState()) {
            h.setState(computed);
        }
    }

    public User getOrganizer(Hackathon hackathon) {
        return hackathon.getParticipations()
                .stream()
                .filter(p -> p.getRole() == Role.ORGANIZER)
                .map(HackathonParticipation::getUser)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Hackathon senza organizzatore"
                ));
    }
}
