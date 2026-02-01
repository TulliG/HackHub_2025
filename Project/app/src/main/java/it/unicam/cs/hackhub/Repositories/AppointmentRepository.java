package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByMentorIdAndHackathonId(Long mentorId, Long hackathonId);
}
