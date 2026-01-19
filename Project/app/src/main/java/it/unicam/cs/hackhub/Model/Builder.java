package it.unicam.cs.hackhub.Model;

import java.time.LocalDateTime;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Interface that defines the behaviour of an {@code HackathonBuilder}.
 */
public interface Builder {

    /**
     * Set the {@code Hackathon}'s name.
     * @param name the name
     */
    public void setName(@NonNull String name);

    /**
     * Set the {@code Hackathon}'s location.
     * @param l the location
     */
    public void setLocation(@NonNull String l);

    /**
     * Set the {@code Hackathon}'s prize
     * @param prize the prize
     */
    public void setPrize(int prize);

    /**
     * Set the {@code Hackathon}'s rules.
     * @param rules the rules
     */
    public void setRules(@NonNull String rules);

    //public void setCreationDate(@NonNull LocalDateTime date);

    /**
     * Set the {@code Hackathon}'s start of the {@code RunningState}.
     * @param date the date
     */
    public void setStartDate(@NonNull LocalDateTime date);

     /**
     * Set the {@code Hackathon}'s start of the {@code EvaluationState}.
     * @param date the date
     */
    public void setEvaluationDate(@NonNull LocalDateTime date);

    /**
     * Set the {@code Hackathon}'s start of the {@code ConcludedState}.
     * @param date the date
     */
    public void setEndingDate(@NonNull LocalDateTime date);

    /**
     * Set the {@code Hackathon}'s minimum number of {@code Team}s to start
     * the {@code Hackathon}.
     * @param teams the number of teams
     */
    public void setMinTeams(int teams);

    /**
     * Set the {@code Hackathon}'s maximum number of {@code Team}s to start
     * the {@code Hackathon}.
     * @param teams the number of teams
     */
    public void setMaxTeams(int teams);

    /**
     * Set the {@code Hackathon}'s minimum number of member per {@code Team}.
     * @param m the number of members per {@code Team}
     */
    public void setMinTeamMembers(int m);

    /**
     * Set the {@code Hackathon}'s maximum number of member per {@code Team}.
     * @param m the number of members per {@code Team}
     */
    public void setMaxTeamMembers(int m);

    /**
     * Set the {@code Hackathon}'s organizer.
     * @param organizer the organizer
     */
    public void setOrganizer(User organizer);

}
