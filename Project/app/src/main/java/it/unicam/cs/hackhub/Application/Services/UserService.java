package it.unicam.cs.hackhub.Application.Services;

import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public List<User> getAvailableUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getParticipation() == null && u.getTeam() == null)
                .toList();
    }

    @Transactional(readOnly = true)
    public User getByUsername(@NonNull String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User '" + username + "' non esiste"));
    }

    @Transactional(readOnly = true)
    public User getById(@NonNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lo user con id " + id + " non esiste"));
    }


    public User create(@NonNull String username, @NonNull String password) {
        if (userRepository.existsByUsername(username)) {
            throw conflict("Username già esistente");
        }
        return userRepository.save(new User(username, password));
    }


    @Transactional(readOnly = true)
    public User checkIfIsAvailable(@NonNull String username) {
        User user = getByUsername(username);

        if (user.getTeam() != null || user.getParticipation() != null) {
            throw conflict("Lo user '" + username + "' non è disponibile");
        }
        return user;
    }


    private ResponseStatusException conflict(String msg) {
        return new ResponseStatusException(HttpStatus.CONFLICT, msg);
    }
}