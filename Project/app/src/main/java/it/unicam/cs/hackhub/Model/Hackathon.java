package it.unicam.cs.hackhub.Model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

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

        private Map<User, Set<Team>> appointments;

        public Calendar() {
            appointments = new HashMap<>();
        }

        public void addAppointment(User user, Team team) {
            Set<Team> teams = appointments.get(user);
            teams.add(team);
        }

    }

    public Hackathon() {}

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

}
