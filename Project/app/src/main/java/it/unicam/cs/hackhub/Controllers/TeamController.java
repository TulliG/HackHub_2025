package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.TeamDTO;
import it.unicam.cs.hackhub.Application.Services.TeamService;
import it.unicam.cs.hackhub.Controllers.Requests.CreateTeamRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/create")
    public TeamDTO createTeam(
            @RequestBody @Valid CreateTeamRequest createTeamRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        return teamService.createTeam(createTeamRequest, userDetails);
    }

    public void quitTeam() {
        //TODO implement: make a User quit from its Team
    }
}
