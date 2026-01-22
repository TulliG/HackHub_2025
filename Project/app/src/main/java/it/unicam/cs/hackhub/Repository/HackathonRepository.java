package it.unicam.cs.hackhub.Repository;

import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class HackathonRepository {

    private static final Map<Long, Hackathon> repo = new HashMap<>();
    private static Long serialId = 1L;

    public Hackathon get(Long id) {
        return repo.get(id);
    }

    public Hackathon put(Hackathon u) {
        if (u.getId() == null) u.setId(serialId++);
        repo.put(u.getId(), u);
        return u;
    }

    public void remove(Long id) {
        repo.remove(id);
    }
}
