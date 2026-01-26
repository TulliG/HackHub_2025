package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface HackathonRepository extends JpaRepository<Hackathon,Long> {

}
