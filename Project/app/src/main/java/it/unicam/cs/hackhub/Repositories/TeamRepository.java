package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entity.Team;

import java.util.HashMap;
import java.util.Map;

public class TeamRepository {

    private static final Map<Long, Team> repo = new HashMap<>();
    private static Long serialId = 1L;

    public Team get(Long id) {
        return repo.get(id);
    }

    public Team put(Team u) {
        if (u.getId() == null) u.setId(serialId++);
        repo.put(u.getId(), u);
        return u;
    }

    public void remove(Long id) {
        repo.remove(id);
    }
}
