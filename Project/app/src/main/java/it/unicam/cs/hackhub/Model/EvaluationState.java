package it.unicam.cs.hackhub.Model;

import java.util.Set;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * State that represents the evaluation state of an {@code Hackathon}.
 */
public class EvaluationState implements HackathonState {

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
    
}
