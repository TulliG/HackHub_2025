package it.unicam.cs.hackhub.Application.Services;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unicam.cs.hackhub.Application.DTOs.ConcludedHackathonDTO;
import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.Exceptions.HackathonCancelledException;
import it.unicam.cs.hackhub.Application.Mappers.HackathonMapper;
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
    private final HackathonMapper mapper;
    private final Clock clock;
    private final TeamRepository teamRepository;
    private final ParticipationRepository participationRepository;
    private final HackathonCancellationService cancellationService;

    public HackathonService(HackathonRepository hackathonRepository,
                            UserService userService,
                            AppointmentRepository appointmentRepository,
                            HackathonMapper mapper,
                            ConcludedHackathonRepository concludedHackathonRepository,
                            Clock clock,
                            TeamRepository teamRepository,
                            ParticipationRepository participationRepository,
                            SubmissionRepository submissionRepository,
                            HackathonCancellationService cancellationService) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
        this.appointmentRepository = appointmentRepository;
        this.mapper = mapper;
        this.concludedHackathonRepository = concludedHackathonRepository;
        this.clock = clock;
        this.teamRepository = teamRepository;
        this.participationRepository = participationRepository;
        this.submissionRepository = submissionRepository;
        this.cancellationService = cancellationService;
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
    public Set<Submission> getSubmissions(@NonNull Long id) {
        return null;
        //return get(id).getSubmissions();
    }

    @Transactional(readOnly = true)
    public Set<Appointment> getAppointments(@NonNull Long hackathonId, @NonNull Long userId) {
        return null; // TODO
    }

    public Hackathon put(@NonNull Hackathon hackathon) {
        return hackathonRepository.save(hackathon);
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

    public void remove(@NonNull Long id) {
        if (!hackathonRepository.existsById(id)) {
            throw new EntityNotFoundException("Hackathon not found with id " + id);
        }
        hackathonRepository.deleteById(id);
    }

    public void createParticipation(HackathonParticipation participation) {
        hackathonRepository.save(participation.getHackathon());
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

    public List<Hackathon> getByState(State state) {
        return hackathonRepository.findAll()
                .stream()
                .filter(h -> h.getState() == state)
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
}
