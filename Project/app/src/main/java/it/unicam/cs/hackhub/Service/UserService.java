package it.unicam.cs.hackhub.Service;

import it.unicam.cs.hackhub.Model.Entity.User;
import it.unicam.cs.hackhub.Repository.UserRepository;

import java.util.List;

public class UserService {

    UserRepository repo = new UserRepository();

    public List<User> getAvailableUsers() {
        return repo.getAll().stream().filter(u -> u.getParticipation() == null).toList();
    }
}
