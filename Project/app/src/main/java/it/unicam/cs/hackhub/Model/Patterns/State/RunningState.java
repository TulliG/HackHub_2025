package it.unicam.cs.hackhub.Model.Patterns.State;

import java.util.Set;

import it.unicam.cs.hackhub.Model.Entity.*;
import it.unicam.cs.hackhub.Model.Enums.Role;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * State that represents the running state of an {@code Hackathon}.
 */
public class RunningState extends HackathonState {

    public RunningState(@NonNull Hackathon hackathon) {
        super(hackathon);
    }

    @Override
    public String getName() {
        return "Running State";
    }

    @Override
    public boolean registerTeam(@NonNull Team t) {
        throw new UnsupportedOperationException("The hackathon is already strated");
    }

    @Override
    public boolean removeTeam(@NonNull Team t) {
        throw new UnsupportedOperationException("The hackathon is already strated");
    }

    @Override
    public boolean addJudge(@NonNull User j) {
        throw new UnsupportedOperationException("The hackathon is already strated");
    }

    @Override
    public boolean addMentor(@NonNull User m) {
        hackathon.setMentor(m);
        m.setParticipation( new HackathonParticipation(hackathon, Role.MENTOR));
        return true;
    }

    @Override
    public boolean submit(@NonNull User u, @NonNull String content) {
        hackathon.addSubmission(new Submission(content, u.getTeam()));
        return true;
    }

    @Override
    public Set<Submission> viewSubmissions() {
        return hackathon.getSubmissions();
    }


    @Override
    public void rateSubmission(@NonNull Submission s) {
        throw new UnsupportedOperationException("Can't rate the submission during the" + this.getName());
    }

    @Override
    public void proclaimWinner(@NonNull Team t) {
        throw new UnsupportedOperationException("Can't proclaim winner during the " + this.getName());
    }

    @Override
    public void sendPrize() {
        throw new UnsupportedOperationException("Can't send prize during the " + this.getName());
    }

    @Override
    public void reserveCall(@NonNull User mentor, @NonNull Team team) {
        // TODO vedere calendar attenzione
    }

    @Override
    public void reportTeam(@NonNull User mentor, @NonNull Team team) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportTeam'");
    }

    public Set<Team> reserveCall(@NonNull User mentor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reserveCall'");
    }

    @Override
    public Set<Team> showAppointments(@NonNull User mentor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showAppointments'");
    }
    
}
