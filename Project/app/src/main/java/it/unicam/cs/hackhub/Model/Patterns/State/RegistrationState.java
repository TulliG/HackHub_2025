package it.unicam.cs.hackhub.Model.Patterns.State;

import java.util.Set;

import it.unicam.cs.hackhub.Model.Entity.*;
import it.unicam.cs.hackhub.Model.Enums.Role;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * State that represents the registration state of an {@code Hackathon}.
 */
public class RegistrationState extends HackathonState {



    public RegistrationState(@NonNull Hackathon hackathon) {
        super(hackathon);
    }

    @Override
    public String getName() {
        return "Registration State";
    }

    @Override
    public boolean registerTeam(@NonNull Team t) {
        hackathon.registerTeam(t);
        for ( User u : t.getMembers()) {
            u.setParticipation(new HackathonParticipation(hackathon, Role.TEAM_MEMBER));
        }
        return true;
    }

    @Override
    public boolean removeTeam(@NonNull Team t) {
        hackathon.removeTeam(t);
        for ( User u : t.getMembers()) {
            u.resetParticipation();
        }
        return true;
    }

    @Override
    public boolean addJudge(@NonNull User j) {
        hackathon.addJudge(j);
        j.setParticipation(new HackathonParticipation(hackathon, Role.JUDGE));
        return true;
    }

    @Override
    public boolean addMentor(@NonNull User m) {
        hackathon.addMentor(m);
        m.setParticipation( new HackathonParticipation(hackathon, Role.MENTOR));
        return true;
    }

    @Override
    public boolean submit(@NonNull User u, @NonNull String content) {
        throw new UnsupportedOperationException("Can't submit during registration");
    }

    @Override
    public Set<Submission> viewSubmissions() {
        throw new UnsupportedOperationException("Can't view submissions during registration");
    }

    @Override
    public void rateSubmission(@NonNull Submission s) {
        throw new UnsupportedOperationException("Can't rate submissions during registration");
    }

    @Override
    public void proclaimWinner(@NonNull Team t) {
        throw new UnsupportedOperationException("Can't proclaim winner during registration");
    }

    @Override
    public void sendPrize() {
        throw new UnsupportedOperationException("Can't send prize during registration");
    }

    @Override
    public void reserveCall(@NonNull User mentor, @NonNull Team team) {
        throw new UnsupportedOperationException("Can't reserve call during registration");
    }

    @Override
    public void reportTeam(@NonNull User mentor, @NonNull Team team) {

    }


    public Set<Team> reserveCall(@NonNull User mentor) {
        throw new UnsupportedOperationException("Can't report call during registration");
    }

    @Override
    public Set<Team> showAppointments(@NonNull User mentor) {
        throw new UnsupportedOperationException("Can't view appointemnts during registration");
    }
    
}
