package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entities.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonRepository extends JpaRepository<Hackathon,Long> {

}
