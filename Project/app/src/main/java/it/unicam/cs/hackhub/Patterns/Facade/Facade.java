package it.unicam.cs.hackhub.Patterns.Facade;

import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.Services.HackathonService;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class Facade {

    private final HackathonService hackathonService;

    public Facade(HackathonService hackathonService) {
        this.hackathonService = hackathonService;
    }

    public void createParticipation() {

    }

    public void sendNotification() {
        //User sender, User receiver, String message
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

}
