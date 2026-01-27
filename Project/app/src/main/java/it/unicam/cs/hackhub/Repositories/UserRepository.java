package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
