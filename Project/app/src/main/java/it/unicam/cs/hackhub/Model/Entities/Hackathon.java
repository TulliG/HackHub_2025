package it.unicam.cs.hackhub.Model.Entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import it.unicam.cs.hackhub.Model.Enums.State;
import jakarta.persistence.*;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings({"FieldMayBeFinal", "unused"})
/*
  Class that defines an {@code Hackathon} inside the {@code Hackhub}
  */
@Entity
@Table(name = "hackathons")
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(nullable = false)
    private String name;

    @Getter
    @Column(nullable = false)
    private String location;

    @Getter
    @Column(nullable = false)
    private String rules;

    @Getter
    @Column(nullable = false)
    private int prize;

    @Getter @Column(nullable = false) private LocalDateTime creationDate;
    @Getter @Column(nullable = false) private LocalDateTime startDate;
    @Getter @Column(nullable = false) private LocalDateTime evaluationDate;
    @Getter @Column(nullable = false) private LocalDateTime endingDate;

    @Getter
    @OneToMany(mappedBy = "hackathon", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<HackathonParticipation> participations = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "hackathon", fetch = FetchType.LAZY)
    private Set<Team> teams = new HashSet<>();

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @Getter
    @OneToMany(mappedBy = "hackathon", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Submission> submissions = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "hackathon", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Appointment> calendar = new HashSet<>();

    public Hackathon() {}

    /**
     * Builds an {@code Hackathon}
     * @param name the name
     * @param location the location
     * @param rules the rules
     * @param prize the prize
     * @param creationDate the creation date
     * @param startDate the start date
     * @param evaluationDate the evaluation date
     * @param endingDate the ending date
     */
    public Hackathon(
            String name, String location, String rules, int prize,
            LocalDateTime creationDate, LocalDateTime startDate,
            LocalDateTime evaluationDate, LocalDateTime endingDate
    ) {
        this.name = name;
        this.location = location;
        this.prize = prize;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.evaluationDate = evaluationDate;
        this.endingDate = endingDate;
        this.state = State.REGISTRATION;
    }

    public void addTeam(@NonNull Team team) {
        teams.add(team);
        team.setHackathon(this);
    }

    public void removeTeam(@NonNull Team team) {
        teams.remove(team);
        team.setHackathon(null);
    }

    public void addSubmission(@NonNull Submission submission) {
        submissions.add(submission);
    }

    public void addParticipation(@NonNull HackathonParticipation participation) {
        participations.add(participation);
    }

    public State computeState(LocalDateTime now) {
        if (now.isBefore(startDate))
            return State.REGISTRATION;
        if (now.isBefore(evaluationDate))
            return State.RUNNING;
        if (now.isBefore(endingDate))
            return State.EVALUATION;
        return State.CONCLUDED;
    }
}
