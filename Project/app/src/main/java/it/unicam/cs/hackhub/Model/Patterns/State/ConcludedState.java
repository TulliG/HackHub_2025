package it.unicam.cs.hackhub.Model.Patterns.State;

import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unicam.cs.hackhub.Model.Entity.Hackathon;
import it.unicam.cs.hackhub.Model.Entity.Submission;
import it.unicam.cs.hackhub.Model.Entity.Team;
import it.unicam.cs.hackhub.Model.Entity.User;

/**
 * State that represents the finish state of an {@code Hackathon}.
 */
public class ConcludedState extends HackathonState {

    public ConcludedState(@NonNull Hackathon hackathon) {
        super(hackathon);
    }

    @Override
    public boolean registerTeam(@NonNull Team t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerTeam'");
    }

    @Override
    public boolean removeTeam(@NonNull Team t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeTeam'");
    }

    @Override
    public boolean addJudge(@NonNull User j) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addJudge'");
    }

    @Override
    public boolean addMentor(@NonNull User m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMentor'");
    }

    @Override
    public boolean submit(@NonNull Submission s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'submit'");
    }

    @Override
    public Set<Submission> viewSubmissions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewSubmissions'");
    }

    @Override
    public void rateSubmission(@NonNull Submission s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateSubmission'");
    }

    @Override
    public void proclaimWinner(@NonNull Team t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'proclaimWinner'");
    }

    @Override
    public void sendPrize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendPrize'");
    }

    @Override
    public void reserveCall(@NonNull User mentor, @NonNull Team team) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reserveCall'");
    }

    @Override
    public void reportTeam(@NonNull Team team) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportTeam'");
    }

    public Set<Team> reserveCall(@NonNull User mentor) {
        return null;
    }

    @Override
    public Set<Team> showAppointments(@NonNull User mentor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showAppointments'");
    }
    
}
