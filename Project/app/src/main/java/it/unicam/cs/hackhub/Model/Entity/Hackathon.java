package it.unicam.cs.hackhub.Model.Entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unicam.cs.hackhub.Model.Patterns.State.HackathonState;

/**
 * Class that defines an {@code Hackathon} inside the {@code Hackhub}
 * */
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

    //TODO fare il main
    private HackathonState state;

    private Team winner = null;

    private Calendar calendar = new Calendar();

    private Set<Submission> submissions = new HashSet<>();

    private class Calendar {

        private Map<User, Set<Team>> appointments = new HashMap<>();

        public Calendar() { }

        public void addAppointment(User user, Team team) {
            Set<Team> teams = appointments.get(user);
            teams.add(team);
        }

    }

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

    public boolean addJudge(@NonNull User user) {
        return state.addJudge(user);
    }

    public boolean addMentor(@NonNull User user) {
        return state.addMentor(user);
    }

    public void proclaimWinner(@NonNull Team team) {
        state.proclaimWinner(team);
    }

    public void rateSubmission(@NonNull Submission submission) {
        state.rateSubmission(submission);
    }

    public boolean registerTeam(@NonNull Team team) {
        return state.registerTeam(team);
    }

    public boolean removeTeam(@NonNull Team team) {
        return state.removeTeam(team);
    }

    public void sendPrize() {
        state.sendPrize();
    }

    public boolean submit(@NonNull Submission submission) {
        return state.submit(submission);
    }

    public Set<Submission> viewSubmissions() {
        return state.viewSubmissions();
    }

    /**
     * Adds the id
     * @param id the id
     */
    public void setId(@NonNull Long id) {
        this.id = id;
    }

    /**
     * Return the current {@code HackathonState}
     * @return the state
     */
    public HackathonState getState() {
        return state;
    }

    /**
     * Adds a {@code Team} to the {@code Calendar} for the mentor
     * @param mentor the mentor
     * @param team the team
     */
    public void reserveCall(@NonNull User mentor, @NonNull Team team) {
        state.reserveCall(mentor, team);
    }

    /**
     * Reports a {@code Team}
     * @param team the team
     */
    public void reportTeam(@NonNull Team team) {
        state.reportTeam(team);
    }

    /**
     * Shwo the appointments in the {@code Calendar} for the mentor
     * @param mentor the mentor
     * @return the teams with an appointment
     */
    public Set<Team> showAppointments(@NonNull User mentor) {
        return state.showAppointments(mentor);
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

    public Calendar getCalendar() {
        return calendar;
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
}