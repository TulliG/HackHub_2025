package it.unicam.cs.hackhub.Services;

import it.unicam.cs.hackhub.Repositories.TeamRepository;
import lombok.NonNull;

/**
 * Service class for managing {@code Team}s
 */
public class TeamService {

    private final TeamRepository repo = new TeamRepository();

    private final UserService userService;

    public TeamService(UserService userService) {
        this.userService = userService;
    }

    public void createNewTeam() {

    }

    public void quitTeam(@NonNull Long userId) {

    }
}
