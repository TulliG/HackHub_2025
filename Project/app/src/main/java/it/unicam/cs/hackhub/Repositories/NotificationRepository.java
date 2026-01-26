package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entity.Notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationRepository {

    private static final Map<Long, Notification> repo = new HashMap<>();
    private static Long serialId = 1L;

    public Notification get(Long id) {
        return repo.get(id);
    }

    public List<Notification> getAll() {
        return repo.values().stream().toList();
    }

    public void put(Notification u) {
        if (u.getId() == null) u.setId(serialId++);
        repo.put(u.getId(), u);
    }

    public void remove(Long id) {
        repo.remove(id);
    }
}
