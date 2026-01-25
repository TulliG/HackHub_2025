package it.unicam.cs.hackhub.Model.Entity;


public class Appointment {

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

    public void setId(Long id) {
        this.id = id;
    }
}
