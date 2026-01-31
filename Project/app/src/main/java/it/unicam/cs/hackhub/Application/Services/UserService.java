package it.unicam.cs.hackhub.Application.Services;

import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final  UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAvailableUsers() {
        return userRepository.findAll().stream().filter(u -> u.getParticipation() == null).toList();
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void create(String username, String password) {
        //TODO: check dello username
        userRepository.save(new User(username, password));
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public User checkIfIsAvailable(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Lo user con username "+username+" non esiste ."
                ));

        if (user.getTeam() != null || user.getParticipation() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Lo user '" + username + "' non è disponibile"
            );
        }
        return user;
    }
}
