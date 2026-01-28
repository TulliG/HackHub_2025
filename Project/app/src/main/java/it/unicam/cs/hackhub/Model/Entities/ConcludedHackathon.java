package it.unicam.cs.hackhub.Model.Entities;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "concluded_hackathons")
public class ConcludedHackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(nullable = false)
    private String name;

    @Getter
    private String location;

    @Getter
    @Column(nullable = false)
    private LocalDateTime startDate;

    @Getter
    @Column(nullable = false)
    private LocalDateTime endingDate;

    @Getter
    @Column(nullable = false)
    private String winnerTeamName;

    protected ConcludedHackathon() {}

    public ConcludedHackathon(Hackathon hackathon, Team winnerTeam) {
        this.name = hackathon.getName();
        this.location = hackathon.getLocation();
        this.startDate = hackathon.getStartDate();
        this.endingDate = hackathon.getEndingDate();
        this.winnerTeamName = winnerTeam.getName();
    }
}
