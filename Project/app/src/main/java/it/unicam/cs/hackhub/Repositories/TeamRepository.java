package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Long countByHackathonId(Long hackathonId);
    List<Team> findAllByHackathonId(Long hackathonId);
}
