package it.unicam.cs.hackhub.Model;

import java.util.Set;

/**
 * Class that defines the {@code Team} that is defined in the {@code Hackhub}
 * and that participate in a {@code Hackathon}.
 * */
public class Team {

    private Long id;

    private String name;

    private Set<User> members;

    public Team() {}

}
