package it.unicam.cs.hackhub.Application.Services;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unicam.cs.hackhub.Application.DTOs.ConcludedHackathonDTO;
import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.Mappers.HackathonMapper;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
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
    private final HackathonMapper mapper;
    private final Clock clock;
    private final TeamRepository teamRepository;
    private final ParticipationRepository participationRepository;

    public HackathonService(HackathonRepository hackathonRepository,
                            UserService userService,
                            AppointmentRepository appointmentRepository,
                            HackathonMapper mapper,
                            ConcludedHackathonRepository concludedHackathonRepository,
                            Clock clock,
                            TeamRepository teamRepository,
                            ParticipationRepository participationRepository) {
        this.hackathonRepository = hackathonRepository;
        this.userService = userService;
        this.appointmentRepository = appointmentRepository;
        this.mapper = mapper;
        this.concludedHackathonRepository = concludedHackathonRepository;
        this.clock = clock;
        this.teamRepository = teamRepository;
        this.participationRepository = participationRepository;
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

    private void cancelledHackathon(Hackathon hackathon, String str) {
        ConcludedHackathon h = new ConcludedHackathon(hackathon, str);
        concludedHackathonRepository.save(h);
        hackathonRepository.delete(hackathon);
    }

    @Transactional
    public void refreshStateIfNeeded(Hackathon h) {
        LocalDateTime localDateTime = LocalDateTime.now(clock);
        State state = h.computeState(localDateTime);
        if (state == h.getState()) return;
        if (state == State.RUNNING) {
            Long teamCount = teamRepository.countByHackathonId(h.getId());
            Long judgeCount
                    = participationRepository.countByHackathonIdAndRole(h.getId(), Role.JUDGE);
            Long mentorCount
                    = participationRepository.countByHackathonIdAndRole(h.getId(), Role.MENTOR);
            if (judgeCount < 1 || mentorCount < 1 || teamCount < 2) {
                cancelledHackathon(h, "cancelled");
                return;
            }
        }
        h.setState(state);
    }

    public List<Hackathon> getByState(State state) {
        return hackathonRepository.findAll()
                .stream()
                .filter(h -> h.getState() == state)
                .toList();
    }




}
