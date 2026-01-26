package it.unicam.cs.hackhub.Services;

import it.unicam.cs.hackhub.Model.Entity.User;
import it.unicam.cs.hackhub.Repositories.UserRepository;

import java.util.List;

public class UserService {

    private final  UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAvailableUsers() {
        return userRepository.findAll().stream().filter(u -> u.getParticipation() == null).toList();
    }
}
