package it.unicam.cs.hackhub.Model.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name = "calendar")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mentor_id", nullable = false)
    private User mentor;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Getter
    @Column(nullable = false)
    private String description;

    protected Appointment() {}

    public Appointment(@NonNull Hackathon hackathon,
                       @NonNull User mentor,
                       @NonNull Team team,
                       @NonNull String description) {
        this.hackathon = hackathon;
        this.mentor = mentor;
        this.team = team;
        this.description = description;
    }
}
