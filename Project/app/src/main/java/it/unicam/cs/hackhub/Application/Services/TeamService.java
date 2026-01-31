package it.unicam.cs.hackhub.Application.Services;

import it.unicam.cs.hackhub.Application.DTOs.TeamDTO;
import it.unicam.cs.hackhub.Application.Mappers.TeamMapper;
import it.unicam.cs.hackhub.Controllers.Requests.CreateTeamRequest;
import it.unicam.cs.hackhub.Model.Entities.Team;
import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Repositories.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for managing {@code Team}s
 */
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;

    public TeamService(TeamRepository teamRepository, UserService userService) {
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    @Transactional
    public Team createTeam(CreateTeamRequest req, UserDetails details) {
        User user = userService.checkIfIsAvailable(details.getUsername());
        Team team = new Team(req.name());
        team.addMember(user);
        teamRepository.save(team);
        return team;
    }

    @Transactional
    public Team getById(Long id){
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Team con id "+id+ "non esiste"
                ));
    }

    @Transactional
    public void quitTeam(@NonNull String username) {
        User user = userService.getByUsername(username);
        Team team = user.getTeam();
        if (team == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Non fai parte di nessun team"
            );
        }

        if (team.getHackathon() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Stai partecipando ad un hackathon, non puoi abbandonare il team"
            );
        }

        team.removeMember(user);
        if (team.getMembers().isEmpty()) {
            teamRepository.delete(team);
        }
    }
}
