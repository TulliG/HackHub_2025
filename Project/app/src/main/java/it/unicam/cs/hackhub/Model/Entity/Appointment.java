package it.unicam.cs.hackhub.Model.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import org.springframework.data.annotation.Id;

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private User mentor;

    @Getter
    private Team team;

    @Getter
    private String description;

    public Appointment(User mentor, Team team, String description) {
        this.mentor = mentor;
        this.team = team;
        this.description = description;
    }

}
