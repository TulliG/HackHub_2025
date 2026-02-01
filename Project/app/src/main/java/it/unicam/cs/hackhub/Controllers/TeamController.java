package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.TeamDTO;
import it.unicam.cs.hackhub.Application.Mappers.TeamMapper;
import it.unicam.cs.hackhub.Application.Services.TeamService;
import it.unicam.cs.hackhub.Controllers.Requests.CreateTeamRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;
    public TeamController(TeamService teamService,  TeamMapper teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @PostMapping("/create")
    public TeamDTO createTeam(
            @RequestBody @Valid CreateTeamRequest createTeamRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        return teamMapper.toDTO(
                teamService.createTeam(createTeamRequest, userDetails)
        );
    }

    @PostMapping("/quit")
    public ResponseEntity<String> quitTeam( @AuthenticationPrincipal UserDetails userDetails) {
        teamService.quitTeam(userDetails.getUsername());
        return ResponseEntity.ok("Team abbandonato correttamente");
    }

    // TODO: dettagli del proprio team
    @GetMapping("/get")
    public TeamDTO getTeamDetails(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return teamMapper.toDTO(teamService.getByMember(userDetails.getUsername()));
    }
}
