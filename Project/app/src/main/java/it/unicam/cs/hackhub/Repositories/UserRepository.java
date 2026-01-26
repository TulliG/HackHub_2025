package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User,Long> {

}
