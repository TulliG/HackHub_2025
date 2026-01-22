package it.unicam.cs.hackhub.Model.Entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.unicam.cs.hackhub.Model.Enums.State;
import org.checkerframework.checker.nullness.qual.NonNull;

@SuppressWarnings({"FieldMayBeFinal", "unused"})
/*
  Class that defines an {@code Hackathon} inside the {@code Hackhub}
  */
public class Hackathon {

    private Long id;

    private String name;

    private String location;

    private String rules;

    private int prize;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime evaluationDate;

    private LocalDateTime endingDate;

    private int minTeams;

    private int maxTeams;

    private int minTeamMembers;

    private int maxTeamMembers;

    private User organizer;

    private User judge = null;

    private Set<User> mentors = new HashSet<>();

    private Set<Team> teams = new HashSet<>();

    private State state;

    private Team winner = null;

    private Set<Submission> submissions = new HashSet<>();

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
     * @param minTeams the minimum number of teams for the {@code Hackathon}
     * @param maxTeams the maximum number of teams for the {@code Hackathon}
     * @param minTeamMembers the minimum number of team members for the {@code Team}
     * @param maxTeamMembers the minimum number of team members for the {@code Team}
     * @param organizer the organizer
     */
    public Hackathon(
            String name, String location, String rules, int prize,
            LocalDateTime creationDate, LocalDateTime startDate, LocalDateTime evaluationDate,
            LocalDateTime endingDate, int minTeams, int maxTeams, int minTeamMembers, int maxTeamMembers,
            User organizer
    ) {
        this.name = name;
        this.location = location;
        this.prize = prize;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.evaluationDate = evaluationDate;
        this.endingDate = endingDate;
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.minTeamMembers = minTeamMembers;
        this.maxTeamMembers = maxTeamMembers;
        this.organizer = organizer;
    }

    /**
     * Return the current {@code HackathonState}
     * @return the state
     */
    public State getState() {
        return state;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getRules() {
        return rules;
    }

    public int getPrize() {
        return prize;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    public int getMinTeams() {
        return minTeams;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public int getMinTeamMembers() {
        return minTeamMembers;
    }

    public int getMaxTeamMembers() {
        return maxTeamMembers;
    }

    public Set<Submission> getSubmissions() {
        return submissions;
    }

    public Team getWinner() {
        return winner;
    }

    public User getOrganizer() {
        return organizer;
    }

    public User getJudge() {
        return judge;
    }

    public Set<User> getMentors() {
        return mentors;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Set<Appointment> getAppointments(User user) {
        return appointments.stream().filter(a -> a.getUser().equals(user)).collect(Collectors.toSet());
    }

    /**
     * Adds the id
     * @param id the id
     */
    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setJudge(@NonNull User judge) {

        this.judge = judge;
    }

    public void setMentor(@NonNull User mentor) {

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
