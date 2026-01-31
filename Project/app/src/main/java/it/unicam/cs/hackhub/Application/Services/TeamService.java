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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing {@code Team}s
 */
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, UserService userService, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.userService = userService;
        this.teamMapper = teamMapper;
    }

    @Transactional
    public TeamDTO createTeam(CreateTeamRequest req, UserDetails details) {
        User user = userService.checkIfIsAvailable(details.getUsername());
        Team team = new Team(req.name());
        team.addMember(user);
        teamRepository.save(team);
        return teamMapper.toDTO(team);
    }

    @Transactional
    public Team getById(Long id){
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Team con id "+id+ "non esiste"
                ));
    }

    public void quitTeam(@NonNull Long userId) {

    }
}
