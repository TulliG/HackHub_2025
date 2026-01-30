package it.unicam.cs.hackhub.Patterns.Builder;

import java.time.LocalDateTime;

import it.unicam.cs.hackhub.Model.Entities.Hackathon;
import lombok.NonNull;

import it.unicam.cs.hackhub.Model.Entities.User;

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

    /**
     * Set the {@code Hackathon}'s creation date.
     * @param date the date
     */
    void setCreationDate(@NonNull LocalDateTime date);

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
     * Creates a hackathon with the setted prameters
     * @return the hackathon created
     */
    Hackathon getResult();

}
