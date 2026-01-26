package it.unicam.cs.hackhub.Model.Entity;

import lombok.NonNull;
import lombok.Setter;

public class Appointment {

    @Setter
    private Long id;

    private User mentor;

    private Team team;

    private String description;

    public Appointment(User mentor, Team team, String description) {
        this.mentor = mentor;
        this.team = team;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    /**
     * Return the mentor
     * @return the mentor
     */
    public User getMentor() {
        return mentor;
    }

    public Team getTeam() {
        return team;
    }

    public String getDescription() {
        return description;
    }

}
