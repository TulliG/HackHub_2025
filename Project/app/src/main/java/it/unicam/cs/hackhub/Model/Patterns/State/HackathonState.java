package it.unicam.cs.hackhub.Model.Patterns.State;

import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import it.unicam.cs.hackhub.Model.Entity.Submission;
import it.unicam.cs.hackhub.Model.Entity.Team;
import it.unicam.cs.hackhub.Model.Entity.User;

/**
 * Abstact class that defines the operations that an {@code Hackathon} can do
 * in a given state.
 */
public abstract class HackathonState {

    protected Hackathon hackathon;

    public HackathonState(@NonNull Hackathon hackathon) {

        this.hackathon = hackathon;
    }

    /**
     * Returns the name of the current state
     * @return the string that represents the state's name
     */
    public abstract String getName();
    
    /**
     * Add a team to the {@code Hackathon}
     * @param t the {@code Team}
     * @return {@code true} iff the team is added correctly from the {@code Hackathon}.
     */
    public abstract boolean registerTeam(@NonNull Team t);

    /**
     * Remove a team from the {@code Hackathon}
     * @param t the {@code Team}
     * @return {@code true} iff the team is removed correctly from the {@code Hackathon}.
     */
    public abstract boolean removeTeam(@NonNull Team t);

    /**
     * Add a {@code User} to the {@code Hackathon} as the {@code Judge}
     * @param j the {@code User}
     * @return {@code true} iff the {@code User} is added correctly as a {@code Judge}
     *         to the {@code Hackathon}.
     */
    public abstract boolean addJudge(@NonNull User j);

    /**
     * Add a {@code User} to the {@code Hackathon} as a {@code Mentor}
     * @param m the {@code User}
     * @return {@code true} iff the {@code User} is added correctly as a {@code Mentor}
     *         to the {@code Hackathon}.
     */
    public abstract boolean addMentor(@NonNull User m);

    /**
     * Add a {@code Submission} to the submission list of the {@code Hackathon}
     * @param u the {@code Submission} of a {@code Team} that does the upload
     * @param content the content of the submission
     * @return {@code true} iff the {@code Team} adds its {@code Submission} to the 
     * {@code Hackathon}.
     */
    public abstract boolean submit(@NonNull User u, @NonNull String content);

    /**
     * View the {@code Submission} list of the {@code Hackathon}
     * @return the submission list.
     */
    public abstract Set<Submission> viewSubmissions();

    /**
     * Rate a {@code Submission} of the submission list.
     * @param s the {@code Submission} of a {@code Team}.
     */
    public abstract void rateSubmission(@NonNull Submission s);

    /**
     * Proclaim the winner of the {@code Hackathon}
     * @param t the {@code Team}.
     */
    public abstract void proclaimWinner(@NonNull Team t);

     /**
     * Send the prize to the winner {@code Team}.
     */
    public abstract void sendPrize();

    /**
     * Reserve a call with a mentor for a {@code Team}
     * @param mentor the mentor
     * @param team the team
     */
    public abstract void reserveCall(@NonNull User mentor, @NonNull Team team);

    /**
     * Resert a {@code Team}
     * @param team the team
     */
    public abstract void reportTeam(@NonNull User mentor, @NonNull Team team);

    /**
     * Show the appointments of a mentor
     * @param mentor the mentor
     * @return the teams with an appointment
     */
    public abstract Set<Team> showAppointments(@NonNull User mentor);

}
