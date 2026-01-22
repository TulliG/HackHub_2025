package it.unicam.cs.hackhub.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import it.unicam.cs.hackhub.Model.Entity.Submission;
import it.unicam.cs.hackhub.Model.Entity.Team;

// TODO aggiungere il CLOCK!!!

/**
 * Service class for managing {@code Hackathon}s and {@code Submission}s
 */
public class HackathonService {

    private final TeamService teamService;

    private static final Map<Long, Hackathon> repo = new HashMap<>();

    private Long serialId = 1L;

    public HackathonService() {
        teamService = new TeamService();
    }
    
    public void createHackathon() {

    }

    public Set<Hackathon> getAll() {
        return new HashSet<>(repo.values());
    }

    public Hackathon getById(@NonNull Long id) {
        return repo.get(id);
    }

    public void registerTeam(@NonNull Long hackathonId, @NonNull Long teamId) {
        Hackathon hackathon = repo.get(hackathonId);
        hackathon.addTeam(teamService.getById(teamId));
    }

    public void cancelRegistration(@NonNull Long hackathonId, @NonNull Long teamId) {
        Hackathon hackathon = repo.get(hackathonId);
        hackathon.removeTeam(teamService.getById(teamId));
    }

    public Set<Submission> getSubmissions(@NonNull Long hackathonId) {
        return getById(hackathonId).getSubmissions();
    }

    public void reserveCall(@NonNull Long senderId, @NonNull Long receiverId) {
        //new NotificationService().sendSupportRequest(UserService.getById(senderId), UserService.getById(senderId));
        return;
    }

    public void reportTeam(@NonNull Long teamId, @NonNull Long hackathonId) {
        return;
    }

    public Set<Team> showAppointments(@NonNull Long hackathonId, @NonNull Long mentorId) {
        return null;
    }

    public void rateSubmission(@NonNull Long hackathonId, @NonNull Long submissionId) {
        Hackathon hackathon = getById(hackathonId);
        
    }

}
