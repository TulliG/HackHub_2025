package it.unicam.cs.hackhub.Controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HackathonController {

    public void createHackathon() {
        //TODO implement: create new Hackathon from builder
    }

    public void getHackathonsInSubscription() {
        //TODO implement: return all Hackathon with Hackthon.getState() == State.SUBSCRIPTION
    }

    public void getAll() {
        //TODO implement: return all Hackathon + ConcludedHackathons
    }

    public void registerTeam() {
        //TODO implement: add a team in Hackathon
    }

    public void uploadSubmission() {
        //TODO implement: add a submission in Hackathon
    }

    public void cancelRegistration() {
        //TODO implement: remove Team from Hackathon
    }

    public void proclaimWinner() {
        //TODO implement: select Hackathon's winner
    }

    public void getMentors() {
        //TODO implement: return all Hackathon's mentors
    }

    public void rateSubmission() {
        //TODO implement: set Submission' s grade
    }

    public void getAppointments() {
        //TODO implement: return all Hackathon's Appointments
    }

    public void getHackathonTeams() {
        //TODO implement: return all Hackathon's Teams
    }

    public void getSubmissions() {
        //TODO implement: return all Hackathon's submissions
    }

    public void sendRequest() {
        //TODO implement: send support request in Hackathon
    }
}
