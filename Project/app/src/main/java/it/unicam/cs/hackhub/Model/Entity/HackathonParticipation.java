package it.unicam.cs.hackhub.Model.Entity;

import lombok.NonNull;

import it.unicam.cs.hackhub.Model.Enums.Role;

public class HackathonParticipation {

    private Long id;

    private User user;

    private Role role;

    private Hackathon hackathon;

    public HackathonParticipation() {}

    /**
     * Creates a {@code HackathonParticipation}.
     *
     * @param hackathon the {@code Hackathon}
     * @param role the user's role in the hackathon
     */
    public HackathonParticipation(@NonNull User user, @NonNull Hackathon hackathon, Role role) {
        this.user = user;
        this.role = role;
        this.hackathon = hackathon;
    }

    /**
     * @return the {@code HackathonParticipation}'s id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the {@code HackathonParticipation}'s {@code User}
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the {@code User}'s role inside the {@code Hackathon}
     */
    public Role getRole() {
        return role;
    }

    /**
     * @return the {@code Hackathon} where the owner participates
     */
    public Hackathon getHackathon() {
        return hackathon;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
