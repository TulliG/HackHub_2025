package it.unicam.cs.hackhub.Repository;

import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import it.unicam.cs.hackhub.Model.Entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private static final Map<Long, User> repo = new HashMap<>();
    private static Long serialId = 1L;

    public User get(Long id) {
        return repo.get(id);
    }

    public List<User> getAll() {
        return repo.values().stream().toList();
    }

    public User put(User u) {
        if (u.getId() == null) u.setId(serialId++);
        repo.put(u.getId(), u);
        return u;
    }

    public void remove(Long id) {
        repo.remove(id);
    }

}
