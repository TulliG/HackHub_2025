package it.unicam.cs.hackhub.Application.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unicam.cs.hackhub.Application.DTOs.ConcludedHackathonDTO;
import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.Mappers.HackathonMapper;
import it.unicam.cs.hackhub.Model.Entities.*;
import it.unicam.cs.hackhub.Repositories.AppointmentRepository;
import it.unicam.cs.hackhub.Repositories.ConcludedHackathonRepository;
import it.unicam.cs.hackhub.Repositories.HackathonRepository;
import it.unicam.cs.hackhub.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO aggiungere il CLOCK!!!

/**
 * Service class for managing {@code Hackathon}s and {@code Submission}s
 */
@Service
@Transactional
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final ConcludedHackathonRepository concludedHackathonRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final HackathonMapper mapper;

    public HackathonService(HackathonRepository hackathonRepository,
                            UserRepository userRepository,
                            AppointmentRepository appointmentRepository,
                            HackathonMapper mapper,
                            ConcludedHackathonRepository concludedHackathonRepository) {
        this.hackathonRepository = hackathonRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.mapper = mapper;
        this.concludedHackathonRepository = concludedHackathonRepository;
    }

    @Transactional(readOnly = true)
    public Hackathon get(@NonNull Long id) {
        return hackathonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hackathon not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<HackathonDTO> getAll() {
        return hackathonRepository
                        .findAll()
                        .stream()
                        .map(mapper::toDTO)
                        .toList();
    }

    @Transactional(readOnly = true)
    public List<ConcludedHackathonDTO> getAllConcluded() {
        return concludedHackathonRepository
                .findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Set<User> getUsers(@NonNull Long id) {
        return null; // TODO
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

    public void remove(@NonNull Long id) {
        if (!hackathonRepository.existsById(id)) {
            throw new EntityNotFoundException("Hackathon not found with id " + id);
        }
        hackathonRepository.deleteById(id);
    }

    private void cancelledHackathon(Hackathon hackathon, String str) {
        ConcludedHackathon h = new ConcludedHackathon(hackathon, str);
        concludedHackathonRepository.save(h);
        hackathonRepository.delete(hackathon);
    }
}
