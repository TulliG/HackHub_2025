package it.unicam.cs.hackhub.Model.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Class that defines a {@code User} that is logged in the {@code HackHub}.
 * */
@Entity
@Table(name = "users")
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, unique = true)
    private String username;

    @Getter
    @Column(nullable = false)
    private String password;

    @Setter
    @Getter
    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private HackathonParticipation participation = null;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team = null;



    public User() {}

    /**
     * Creates a {@code User} by its username and password.
     *
     * @param username the {@code User}'s username
     * @param password the {@code User}'s password
     */
    public User(@NonNull String username, @NonNull String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty())
            throw new IllegalArgumentException("Invalid username and/or password");
        this.username = username.trim();
        this.password = password.trim();
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
}
