package it.unicam.cs.hackhub.Model.Entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import it.unicam.cs.hackhub.Model.Enums.State;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@SuppressWarnings({"FieldMayBeFinal", "unused"})
/*
  Class that defines an {@code Hackathon} inside the {@code Hackhub}
  */
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private String location;

    @Getter
    private String rules;

    @Getter
    private int prize;

    @Getter
    private LocalDateTime creationDate;

    @Getter
    private LocalDateTime startDate;

    @Getter
    private LocalDateTime evaluationDate;

    @Getter
    private LocalDateTime endingDate;

    @Getter
    private User organizer;

    @Getter
    private User judge = null;

    @Getter
    private Set<User> mentors = new HashSet<>();

    @Getter
    private Set<Team> teams = new HashSet<>();

    @Getter
    @Setter
    private State state;

    @Getter
    @Setter
    private Team winner = null;

    @Getter
    private Set<Submission> submissions = new HashSet<>();

    @Getter
    private Set<Appointment> appointments = new HashSet<>();

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
     * @param organizer the organizer
     */
    public Hackathon(
            String name, String location, String rules, int prize,
            LocalDateTime creationDate, LocalDateTime startDate, LocalDateTime evaluationDate,
            LocalDateTime endingDate, User organizer
    ) {
        this.name = name;
        this.location = location;
        this.prize = prize;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.evaluationDate = evaluationDate;
        this.endingDate = endingDate;
        this.organizer = organizer;
    }

    /**
     * Adds the id
     * @param id the id
     */
    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setJudge(@NonNull User judge) {
	if (this.judge != null)
            this.judge = judge;
    }

    public void addMentor(@NonNull User mentor) {

        this.mentors.add(mentor);
    }

    public void addTeam(@NonNull Team team) {
        teams.add(team);
    }

    public void addSubmission(@NonNull Submission submission) {

        submissions.add(submission);
    }

    public void removeTeam(@NonNull Team team) {
        teams.remove(team);
    }
}
