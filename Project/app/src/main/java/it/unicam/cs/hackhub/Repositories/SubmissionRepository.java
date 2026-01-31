package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("""
        select s from Submission s
        where s.hackathon.id = :hackathonId
          and s.submissioner.id = :teamId
    """)
    Optional<Submission> findByHackathonIdAndTeamId(Long hackathonId, Long teamId);
}
