package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

public interface TeamRepository extends JpaRepository<Team,Long> {

}
