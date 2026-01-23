package it.unicam.cs.hackhub.Service;

import it.unicam.cs.hackhub.Repository.TeamRepository;
import org.checkerframework.checker.nullness.qual.NonNull;

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
