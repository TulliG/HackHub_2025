package it.unicam.cs.hackhub.Model.Entity;


public class Appointment {

    private Long id;

    private User user;

    private Team team;

    private String description;

    public Appointment(User user, Team team, String description) {
        this.user = user;
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
    public User getUser() {
        return user;
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
