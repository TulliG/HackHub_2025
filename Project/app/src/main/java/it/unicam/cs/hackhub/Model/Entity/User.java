package it.unicam.cs.hackhub.Model.Entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Class that defines a {@code User} that is logged in the {@code HackHub}.
 * */
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String username;

    private String password;

    @Setter
    @Getter
    private HackathonParticipation participation = null;

    @Setter
    @Getter
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

    public void resetParticipation() {
        this.participation = null;
    }

}
