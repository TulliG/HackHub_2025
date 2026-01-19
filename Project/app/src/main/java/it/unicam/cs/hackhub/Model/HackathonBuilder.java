package it.unicam.cs.hackhub.Model;

import java.time.LocalDateTime;

import org.checkerframework.checker.nullness.qual.NonNull;

public class HackathonBuilder implements Builder {

    private String name;

    private String location;

    private String rules;

    private int prize;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime startDate;

    private LocalDateTime evaluationDate;

    private LocalDateTime endingDate;

    private int minTeams;

    private int maxTeams;

    private int minTeamMembers;

    private int maxTeamMembers;

    private User organizer;

    @Override
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public void setLocation(@NonNull String l) {
        this.location = l;
    }

    @Override
    public void setPrize(int prize) {
        this.prize = prize;
    }

    @Override
    public void setRules(@NonNull String rules) {
       this.rules = rules;
    }

    @Override
    public void setStartDate(@NonNull LocalDateTime date) {
        this.startDate = date;
    }

    @Override
    public void setEvaluationDate(@NonNull LocalDateTime date) {
        this.evaluationDate = date;
    }

    @Override
    public void setEndingDate(@NonNull LocalDateTime date) {
       this.endingDate = date;
    }

    @Override
    public void setMinTeams(int teams) {
        this.minTeams = teams;
    }

    @Override
    public void setMaxTeams(int teams) {
        this.maxTeams = teams;
    }

    @Override
    public void setMinTeamMembers(int m) {
        this.minTeamMembers = m;
    }

    @Override
    public void setMaxTeamMembers(int m) {
        this.maxTeamMembers = m;
    }

    @Override
    public void setOrganizer(@NonNull User organizer) {
        this.organizer = organizer;
    }

    public Hackathon getResult() {
        return null;
    }

}
