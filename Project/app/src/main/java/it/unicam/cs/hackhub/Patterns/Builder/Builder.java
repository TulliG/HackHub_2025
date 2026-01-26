package it.unicam.cs.hackhub.Patterns.Builder;

import java.time.LocalDateTime;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unicam.cs.hackhub.Model.Entity.User;

/**
 * Interface that defines the behaviour of an {@code HackathonBuilder}.
 */
public interface Builder {

    /**
     * Set the {@code Hackathon}'s name.
     * @param name the name
     */
    void setName(@NonNull String name);

    /**
     * Set the {@code Hackathon}'s location.
     * @param l the location
     */
    void setLocation(@NonNull String l);

    /**
     * Set the {@code Hackathon}'s prize
     * @param prize the prize
     */
    void setPrize(int prize);

    /**
     * Set the {@code Hackathon}'s rules.
     * @param rules the rules
     */
    void setRules(@NonNull String rules);

    //public void setCreationDate(@NonNull LocalDateTime date);

    /**
     * Set the {@code Hackathon}'s start of the {@code RunningState}.
     * @param date the date
     */
    void setStartDate(@NonNull LocalDateTime date);

     /**
     * Set the {@code Hackathon}'s start of the {@code EvaluationState}.
     * @param date the date
     */
    void setEvaluationDate(@NonNull LocalDateTime date);

    /**
     * Set the {@code Hackathon}'s start of the {@code ConcludedState}.
     * @param date the date
     */
    void setEndingDate(@NonNull LocalDateTime date);

    /**
     * Set the {@code Hackathon}'s minimum number of {@code Team}s to start
     * the {@code Hackathon}.
     * @param teams the number of teams
     */
    void setMinTeams(int teams);

    /**
     * Set the {@code Hackathon}'s maximum number of {@code Team}s to start
     * the {@code Hackathon}.
     * @param teams the number of teams
     */
    void setMaxTeams(int teams);

    /**
     * Set the {@code Hackathon}'s minimum number of member per {@code Team}.
     * @param m the number of members per {@code Team}
     */
    void setMinTeamMembers(int m);

    /**
     * Set the {@code Hackathon}'s maximum number of member per {@code Team}.
     * @param m the number of members per {@code Team}
     */
    void setMaxTeamMembers(int m);

    /**
     * Set the {@code Hackathon}'s organizer.
     * @param organizer the organizer
     */
    void setOrganizer(User organizer);

}
