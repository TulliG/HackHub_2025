package it.unicam.cs.hackhub.Service;

import it.unicam.cs.hackhub.Model.Entity.HackathonParticipation;
import it.unicam.cs.hackhub.Model.Entity.User;
import it.unicam.cs.hackhub.Model.Enums.Role;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserService {

    private static final Map<Long, User> userRepo = new HashMap<>();
    private static final Map<Long, HackathonParticipation> participationRepo = new HashMap<>();

    private Long newUserId = 1L;
    private Long newParticipationId = 1L;

    public User createUser(@NonNull String username,@NonNull String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty())
            throw new IllegalArgumentException("user must have a username and a password");
        User result = new User(username, password);
        result.setId(newUserId);
        userRepo.put(newUserId++, result);
        return result;
    }

    public void createParticipation(@NonNull Long userId, @NonNull Long hackathonId, @NonNull Role userRole) {
        if (!userRepo.containsKey(userId) || new HackathonService().getById(hackathonId) == null)
            throw new IllegalArgumentException("not all entities exists");
        HackathonParticipation result = new HackathonParticipation(new HackathonService().getById(hackathonId), userRole);
        result.setId(newParticipationId);
        participationRepo.put(newParticipationId++, result);
        userRepo.get(userId).setParticipation(result);
    }

    public boolean removeUser(@NonNull Long id) {
        return userRepo.remove(id) != null;
    }

    public boolean removeParticipation(@NonNull Long id) {
        return participationRepo.remove(id) != null;
    }

    public User getById(@NonNull Long id) {
        return userRepo.get(id);
    }

    public HackathonParticipation getParticipationById(@NonNull Long id) {
        return participationRepo.get(id);
    }

    public Set<User> getAllUsers() {
        return new HashSet<>(userRepo.values());
    }

    public Set<HackathonParticipation> getAllParticipations() {
        return new HashSet<>(participationRepo.values());
    }

}
