package it.unicam.cs.hackhub.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import it.unicam.cs.hackhub.Model.Entity.Submission;
import it.unicam.cs.hackhub.Model.Entity.Team;
import it.unicam.cs.hackhub.Model.Entity.User;

// TODO aggiungere il CLOCK!!!

/**
 * Service class for managing {@code Hackathon}s and {@code Submission}s
 */
public class HackathonService {

    private static final Map<Long, Hackathon> repo = new HashMap<>();
    private Long serialId = 1L;
    
    public void createHackathon() {

    }

    public Set<Hackathon> getAll() {
        return new HashSet<>(repo.values());
    }

    public Hackathon getById(@NonNull Long id) {
        return repo.get(id);
    }

    public void registerTeam(@NonNull Hackathon hackathon, @NonNull Team team) {
        for (Hackathon h : repo.values())
            if (h.equals(hackathon))
                h.registerTeam(team);
    }

    public void cancelRegistration(@NonNull Hackathon hackathon, @NonNull Team team) {
        for (Hackathon h : repo.values())
            if (h.equals(hackathon))
                h.removeTeam(team);
    }

    public Set<Submission> getSubmissions(@NonNull Hackathon hackathon) {
        for (Hackathon h : repo.values())
            if (h.equals(hackathon))
                return h.viewSubmissions();
        return null;
    }

    public void reserveCall(@NonNull User sender, @NonNull User receiver) {
        new NotificationService().sendSupportRequest(sender, receiver);
        return;
    }

    public void reportTeam(@NonNull Team team, @NonNull Hackathon hackathon) {
        return;
    }

    public Set<Team> showAppointments(@NonNull Hackathon hackathon, @NonNull User mentor) {
        return null;
    }

    public void rateSubmission(@NonNull Hackathon hackathon, @NonNull Submission submission) {
        for (Hackathon h : repo.values())
            if (h.equals(hackathon))
                h.rateSubmission(submission);
    }

}
