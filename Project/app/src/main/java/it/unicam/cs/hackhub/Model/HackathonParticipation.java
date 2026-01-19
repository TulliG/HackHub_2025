package it.unicam.cs.hackhub.Model;

import org.checkerframework.checker.nullness.qual.NonNull;

public class HackathonParticipation {

    private Long id;

    private User user;

    private Role role;

    private Team team;

    private Hackathon hackathon;

    public HackathonParticipation() {}

    /**
     * Creates a {@code HackathonParticipation} as a staff member.
     *
     * @param user the participating user
     * @param hackathon the {@code Hackathon}
     * @param role the user's staffing role
     * @throws IllegalArgumentException if the role is not staffing related
     */
    public HackathonParticipation(@NonNull User user, @NonNull Hackathon hackathon, @NonNull Role role) {
        if (!role.isStaff()) throw new IllegalArgumentException("Invalid role");
        this.user = user;
        this.role = role;
        this.team = null;
        this.hackathon = hackathon;
    }

    /**
     * Creates a {@code HackathonParticipation} as a team member.
     *
     * @param user the participating user
     * @param hackathon the {@code Hackathon}
     * @throws IllegalArgumentException if the user has not a team
     */
    public HackathonParticipation(@NonNull User user, @NonNull Hackathon hackathon) {
        if (user.getTeam() == null) throw new IllegalArgumentException("the user cannot participate without a team");
        this.user = user;
        this.role = Role.TEAM_MEMBER;
        this.team = user.getTeam();
        this.hackathon = hackathon;
    }

    /**
     * @return the {@code HackathonParticipation}'s id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the {@code HackathonParticipation}'s owner
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
     * @return the owner's team or {@code null} if the owner is a staff member
     */
    public Team getTeam() {
        return team;
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
