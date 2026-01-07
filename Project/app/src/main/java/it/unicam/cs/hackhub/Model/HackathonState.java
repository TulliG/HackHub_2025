package it.unicam.cs.hackhub.Model;

import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Interface that defines the operations that an {@code Hackathon} can do
 * in a given state.
 */
public interface HackathonState {
    
    /**
     * Add a team to the {@code Hackathon}
     * @param t the {@code Team}
     * @return {@code true} iff the team is added correctly from the {@code Hackathon}.
     */
    public boolean registerTeam(@NonNull Team t);

    /**
     * Remove a team from the {@code Hackathon}
     * @param t the {@code Team}
     * @return {@code true} iff the team is removed correctly from the {@code Hackathon}.
     */
    public boolean removeTeam(@NonNull Team t);

    /**
     * Add a {@code User} to the {@code Hackathon} as the {@code Judge}
     * @param j the {@code User}
     * @return {@code true} iff the {@code User} is added correctly as a {@code Judge}
     *         to the {@code Hackathon}.
     */
    public boolean addJudge(@NonNull User j);

    /**
     * Add a {@code User} to the {@code Hackathon} as a {@code Mentor}
     * @param m the {@code User}
     * @return {@code true} iff the {@code User} is added correctly as a {@code Mentor}
     *         to the {@code Hackathon}.
     */
    public boolean addMentor(@NonNull User m);

    /**
     * Add a {@code Submission} to the submission list of the {@code Hackathon}
     * @param s the {@code Submission} of a {@code Team}
     * @return {@code true} iff the {@code Team} adds its {@code Submission} to the 
     * {@code Hackathon}.
     */
    public boolean submit(@NonNull Submission s);

    /**
     * View the {@code Submission} list of the {@code Hackathon}
     * @return the submission list.
     */
    public Set<Submission> viewSubmissions();

    /**
     * Rate a {@code Submission} of the submission list.
     * @param s the {@code Submission} of a {@code Team}.
     */
    public void rateSubmission(@NonNull Submission s);

    /**
     * Proclaim the winner of the {@code Hackathon}
     * @param t the {@code Team}.
     */
    public void proclaimWinner(@NonNull Team t);

     /**
     * Send the prize to the winner {@code Team}.
     */
    public void sendPrize();

}
