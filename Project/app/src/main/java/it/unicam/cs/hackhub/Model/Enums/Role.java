package it.unicam.cs.hackhub.Model;

/**
 * Enumerator that defines the {@code Role} of a {@code User}
 * within an {@code Hackathon}.
 */
public enum Role {

    TEAM_MEMBER,
    ORGANIZER,
    JUDGE,
    MENTOR;

    public boolean isStaff() {
        return this != TEAM_MEMBER;
    }
}
