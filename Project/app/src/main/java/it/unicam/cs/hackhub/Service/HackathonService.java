package it.unicam.cs.hackhub.Service;

import java.util.HashSet;
import java.util.Set;

import it.unicam.cs.hackhub.Model.Entity.Appointment;
import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import it.unicam.cs.hackhub.Model.Entity.Submission;
import it.unicam.cs.hackhub.Model.Entity.User;
import it.unicam.cs.hackhub.Repository.HackathonRepository;
import it.unicam.cs.hackhub.Repository.UserRepository;

// TODO aggiungere il CLOCK!!!

/**
 * Service class for managing {@code Hackathon}s and {@code Submission}s
 */
public class HackathonService {

    HackathonRepository hackathonRepository = new HackathonRepository();
    UserRepository userRepository = new UserRepository();

    public Hackathon get(Long id) {
        return hackathonRepository.get(id);
    }

    public Set<Hackathon> getAll() {
        return new HashSet<>(hackathonRepository.getAll());
    }

    public Set<User> getUsers(Long id) {
        return hackathonRepository.get(id).getMentors();
    }

    public Set<Submission> getSubmissions(Long id) {
        return hackathonRepository.get(id).getSubmissions();
    }

    public Set<Appointment> getAppointments(Long hackathonId, Long userId) {
        return hackathonRepository.get(hackathonId);
    }

    public void put(Hackathon hackathon) {
        hackathonRepository.put(hackathon);
    }

    public void remove(Long id) {
        hackathonRepository.remove(id);
    }

}
