package it.unicam.cs.hackhub.Application.Services;

import it.unicam.cs.hackhub.Model.Entities.*;
import it.unicam.cs.hackhub.Repositories.ConcludedHackathonRepository;
import it.unicam.cs.hackhub.Repositories.HackathonRepository;
import it.unicam.cs.hackhub.Repositories.ParticipationRepository;
import it.unicam.cs.hackhub.Repositories.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HackathonCancellationService {

    private final HackathonRepository hackathonRepository;
    private final ConcludedHackathonRepository concludedHackathonRepository;
    private final ParticipationRepository participationRepository;
    private final TeamRepository teamRepository;

    public HackathonCancellationService(
            HackathonRepository hackathonRepository,
            ConcludedHackathonRepository concludedHackathonRepository,
            ParticipationRepository participationRepository,
            TeamRepository teamRepository
    ) {
        this.hackathonRepository = hackathonRepository;
        this.concludedHackathonRepository = concludedHackathonRepository;
        this.participationRepository = participationRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelHackathon(Long hackathonId, String reason) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hackathon non trovato"));

        concludedHackathonRepository.save(new ConcludedHackathon(hackathon, reason));

        detachParticipations(hackathonId);
        detachTeams(hackathonId);

        hackathon.getTeams().clear();
        hackathon.getSubmissions().clear();
        hackathon.getCalendar().clear();

        hackathonRepository.delete(hackathon);
        hackathonRepository.flush();
    }

    private void detachParticipations(Long hackathonId) {
        List<HackathonParticipation> parts = participationRepository.findAllByHackathonId(hackathonId);
        for (HackathonParticipation p : parts) {
            if (p.getUser() != null) {
                p.getUser().setParticipation(null);
            }
        }
        participationRepository.deleteAll(parts);
    }

    private void detachTeams(Long hackathonId) {
        List<Team> teams = teamRepository.findAllByHackathonId(hackathonId);
        for (Team t : teams) {
            t.setHackathon(null);
        }
    }
}
