package it.unicam.cs.hackhub.Model;

/**
 * Class that defines a {@code User} that is logged in the {@code HackHub}.
 * */
public class User {

    private Long id;

    private String username;

    private String password;

    private HackathonParticipation participation;

    private Team team;

    public User() {}

}
