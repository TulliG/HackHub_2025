package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entities.HackathonParticipation;
import it.unicam.cs.hackhub.Model.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<HackathonParticipation, Long> {

    Long countByHackathonIdAndRole(Long hackathonId, Role role);
    List<HackathonParticipation> findAllByHackathonId(Long hackathonId);

}
