package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.Services.HackathonService;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
import it.unicam.cs.hackhub.Patterns.Facade.Facade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hackathon")
public class HackathonController {

    private final HackathonService service;
    private final Facade facade;

    public HackathonController(HackathonService service, Facade facade) {
        this.service = service;
        this.facade = facade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HackathonDTO createHackathon(CreateHackathonRequest request) {
        facade.createHackathon();
        return null;
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
