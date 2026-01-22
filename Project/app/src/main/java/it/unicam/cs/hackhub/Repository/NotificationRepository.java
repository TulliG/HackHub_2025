package it.unicam.cs.hackhub.Repository;

import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import it.unicam.cs.hackhub.Model.Entity.Notification;
import it.unicam.cs.hackhub.Model.Entity.Team;

import java.util.HashMap;
import java.util.Map;

public class NotificationRepository {

    private static final Map<Long, Notification> repo = new HashMap<>();
    private static Long serialId = 1L;

    public Notification get(Long id) {
        return repo.get(id);
    }

    public Notification put(Notification u) {
        if (u.getId() == null) u.setId(serialId++);
        repo.put(u.getId(), u);
        return u;
    }

    public void remove(Long id) {
        repo.remove(id);
    }
}
