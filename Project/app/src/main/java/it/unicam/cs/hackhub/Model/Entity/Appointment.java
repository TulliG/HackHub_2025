package it.unicam.cs.hackhub.Model.Entity;

import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;

public class Appointment {

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
