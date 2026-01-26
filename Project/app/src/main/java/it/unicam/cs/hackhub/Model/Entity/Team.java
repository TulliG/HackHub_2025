package it.unicam.cs.hackhub.Model.Entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.NonNull;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that defines the {@code Team} that is defined in the {@code Hackhub}
 * and that participate in a {@code Hackathon}.
 * */
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id = null;

    @Getter
    private String name;

    @Getter
    private final Set<User> members = new HashSet<>();

    public Team() {}

    /**
     * Creates a {@code Team} by its name.
     *
     * @param name the given name
     * @throws IllegalArgumentException if name is empty
     */
    public Team(@NonNull String name) {
        if (name.trim().isEmpty())
            throw new IllegalArgumentException("Invalid name");
        this.name = name;
    }

    /**
     * Adds a new {@code User} as a {@code Team}'s member
     *
     * @param user the {@code User} to add
     */
    public void addMember(@NonNull User user) {
        if (user.getTeam() != null)
            throw new IllegalArgumentException("the user is already part of a team");
        members.add(user);
    }

    /**
     * Removes a {@code Team}'s member
     *
     * @param user the member to remove
     */
    public void removeMember(@NonNull User user) {
        members.remove(user);
    }

}
