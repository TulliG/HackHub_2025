package it.unicam.cs.hackhub.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unicam.cs.hackhub.Model.Team;
import it.unicam.cs.hackhub.Model.User;

/**
 * Service class for managing {@code Team}s
 */
public class TeamService {

    private Map<Long, Team> repo = new HashMap<>();
    private Long serialId = 1L;

    /**
     * Return all teams
     * @return the teams
     */
    public Set<Team> getAll() {
        return new HashSet<>(repo.values());
    }

    /**
     * Get {@code Team} by id
     * @param id the id
     * @return the {@code Team}
     */
    public Team getById(@NonNull Long id) {
        return repo.get(id);
    }

    /**
     * Create a {@code Team}
     * @param name the name of the {@code Team}
     * @param user the user
     * @return {@code Team} created
     * @throws IllegalArgumentException if the name is already taken
     */
    public Team createTeam(@NonNull String name, @NonNull User user) {
        for (Team team : repo.values())
            if (team.getName().equals(name))
                throw new IllegalArgumentException("Name already taken.");
        Team team = new Team(name);
        team.setId(serialId);
        team.addMember(user);
        repo.put(serialId, team);
        serialId++;
        return team;
    }

    /**
     * Remove the {@code Team} by id
     * @param id the id
     */
    public void deleteById(@NonNull Long id) {
        repo.remove(id);
    }

    /**
     * Add a {@code User} to a {@code Team}
     * @param user the {@code User}
     * @param team the {@code Team}
     */
    public void addMember(@NonNull User user, @NonNull Team team) {
        Team t = null;
        for (Team t2 : repo.values())
            if (t2.equals(team))
                t = t2;
        if (t == null) return;
        t.addMember(user);
    }

    /**
     * Remove a {@code User} from the {@code Team}
     * @param userg the {@code User}
     * @throws IllegalStateException if tring to remove a {@code User} during an {@code Hackathon}
     */
    public void quitTeam(@NonNull User user) {
        if (user.getParticipation() != null)
            throw new IllegalStateException("Can't remove a user during an hackathon.");
        for (Team team : repo.values())
            if (team.equals(user.getTeam())) {
                team.removeMember(user);
                if (team.getMembers().size() == 0)
                    repo.remove(team.getId());
                return;
            }   
    }
    
}
