package it.unicam.cs.hackhub.Model.Patterns.Facade;

public class Facade {


    public void createParticipation() {

    }

    public void acceptTeamInvite() {
        //TODO available user check
    }

    public void acceptJudgeInvite() {
        //TODO hackathon state check(REGISTRATION)
        //TODO available user check
    }

    public void acceptMentorInvite() {
        //TODO hackathon state check(REGISTRATION, RUNNING)
        //TODO available user check
    }

    public void acceptSupportRequest() {
        //TODO hackathon state check(RUNNING)
    }

    public void createHackathon() {
        //TODO available user check
    }
}
