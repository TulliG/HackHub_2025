package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entities.HackathonParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<HackathonParticipation,Long> {

}
