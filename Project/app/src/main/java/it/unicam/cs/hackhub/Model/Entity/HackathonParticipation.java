package it.unicam.cs.hackhub.Model.Entity;

import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import lombok.NonNull;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import it.unicam.cs.hackhub.Model.Enums.Role;

public class HackathonParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private User user;

    @Getter
    private Role role;

    @Getter
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

}
