package it.unicam.cs.hackhub.Model;

import java.time.LocalDateTime;
import java.util.HashMap;
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

    private int prize;

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime evaluationDate;

    private LocalDateTime endingDate;

    private int minTeams;

    private int maxTeams;

    private int minTeamMembers;

    private int maxteamMembers;

    private User organizer;

    private User judge;

    private Set<User> mentors;

    private Set<Team> teams;

    private HackathonState state;

    private Team winner;

    private Calendar calendar;

    private Set<Submission> submissions;

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

}
