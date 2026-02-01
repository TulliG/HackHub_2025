package it.unicam.cs.hackhub.Application.Services;

import it.unicam.cs.hackhub.Controllers.Requests.CreateTeamRequest;
import it.unicam.cs.hackhub.Model.Entities.Team;
import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Repositories.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;

    public TeamService(TeamRepository teamRepository, UserService userService) {
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    public Team createTeam(@NonNull CreateTeamRequest req, @NonNull UserDetails details) {
        User user = userService.checkIfIsAvailable(details.getUsername());

        String name = req.name();
        if (teamRepository.existsByName(name)) {
            throw conflict("Esiste già un team con nome " + name);
        }

        Team team = new Team(name);
        team.addMember(user);

        return teamRepository.save(team);
    }

    public void quitTeam(@NonNull String username) {
        User user = userService.getByUsername(username);
        Team team = requireTeam(user);

        if (team.getHackathon() != null) {
            throw conflict("Stai partecipando ad un hackathon, non puoi abbandonare il team");
        }

        team.removeMember(user);

        if (team.getMembers().isEmpty()) {
            teamRepository.delete(team);
        }
    }

    @Transactional(readOnly = true)
    public Team getById(@NonNull Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team con id " + id + " non esiste"));
    }

    @Transactional(readOnly = true)
    public Team getByMember(@NonNull String username) {
        User user = userService.getByUsername(username);
        return requireTeam(user);
    }


    private Team requireTeam(User user) {
        Team team = user.getTeam();
        if (team == null) {
            throw conflict("Non fai parte di nessun team");
        }
        return team;
    }

    private ResponseStatusException conflict(String msg) {
        return new ResponseStatusException(HttpStatus.CONFLICT, msg);
    }
}