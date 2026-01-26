package it.unicam.cs.hackhub.Services;

import it.unicam.cs.hackhub.Repositories.TeamRepository;
import lombok.NonNull;

/**
 * Service class for managing {@code Team}s
 */
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;

    public TeamService(TeamRepository teamRepository, UserService userService) {
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    public void createNewTeam() {

    }

    public void quitTeam(@NonNull Long userId) {

    }
}
