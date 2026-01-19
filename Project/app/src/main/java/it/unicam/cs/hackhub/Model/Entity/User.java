package it.unicam.cs.hackhub.Model.Entity;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Class that defines a {@code User} that is logged in the {@code HackHub}.
 * */
public class User {

    private Long id;

    private String username;

    private String password;

    private HackathonParticipation participation = null;

    private Team team = null;

    public User() {}

    /**
     * Creates a {@code User} by its username and password.
     *
     * @param username the {@code User}'s username
     * @param password the {@code User}'s password
     */
    public User(@NonNull String username,@NonNull String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty())
            throw new IllegalArgumentException("Invalid username and/or password");
        this.username = username.trim();
        this.password = password.trim();
    }

    /**
     * @return the {@code User}'s password
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the {@code User}'s username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Checks if the given {@code String} matches the {@code User}'s password
     *
     * @param password the {@code String} to match
     * @return {@code true} iff there is a match
     */
    public boolean isPasswordMatch(String password) {
        return this.password.equals(password);
    }

    /**
     * @return the {@code User}'s {@code HackathonParticipation}
     */
    public HackathonParticipation getParticipation() {
        return participation;
    }

    //TODO public boolean isTeamMember()
    /**
     * @return the {@code User}'s {@code Team}
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sets a new {@code User}'s {@code HackathonParticipation}
     *
     * @param participation the new {@code HackathonParticipation}
     */
    public void setParticipation(HackathonParticipation participation) {
        this.participation = participation;
    }

    /**
     * Sets a new {@code User}'s {@code Team}
     *
     * @param team the new {@code Team}
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
