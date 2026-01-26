package it.unicam.cs.hackhub.Repositories;

import java.util.HashMap;
import java.util.Map;

import it.unicam.cs.hackhub.Model.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission,Long> {

}
