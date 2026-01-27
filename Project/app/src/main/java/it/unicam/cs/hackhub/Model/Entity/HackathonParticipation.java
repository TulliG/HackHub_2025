package it.unicam.cs.hackhub.Model.Entity;

import jakarta.persistence.*;
import lombok.NonNull;
import lombok.Getter;

import it.unicam.cs.hackhub.Model.Enums.Role;
import lombok.Setter;

@Entity
@Table(
        name = "participations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id")
        }
)
public class HackathonParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id", nullable = false)
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
