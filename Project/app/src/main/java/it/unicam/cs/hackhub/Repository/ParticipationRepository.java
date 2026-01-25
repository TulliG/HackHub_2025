package it.unicam.cs.hackhub.Repository;

import java.util.HashMap;
import java.util.Map;

import it.unicam.cs.hackhub.Model.Entity.HackathonParticipation;

public class ParticipationRepository {

    private static final Map<Long, HackathonParticipation> repo = new HashMap<>();
    private static Long serialId = 1L;

    public HackathonParticipation get(Long id) {
        return repo.get(id);
    }

    public void put(HackathonParticipation hp) {
        if (hp.getId() == null) hp.setId(serialId++);
        repo.put(hp.getId(), hp);
    }

    public void remove(Long id) {
        repo.remove(id);
    }
    
}
