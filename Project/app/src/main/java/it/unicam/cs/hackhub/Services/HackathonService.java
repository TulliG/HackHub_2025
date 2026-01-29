package it.unicam.cs.hackhub.Services;

import java.util.HashSet;
import java.util.Set;

import it.unicam.cs.hackhub.Model.Entities.Appointment;
import it.unicam.cs.hackhub.Model.Entities.Hackathon;
import it.unicam.cs.hackhub.Model.Entities.Submission;
import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Repositories.AppointmentRepository;
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
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public HackathonService(HackathonRepository hackathonRepository, UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.hackathonRepository = hackathonRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    public Hackathon get(@NonNull Long id) {
        return hackathonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hackathon not found with id " + id));
    }

    @Transactional(readOnly = true)
    public Set<Hackathon> getAll() {
        return new HashSet<>(hackathonRepository.findAll());
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
}
