package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.ConcludedHackathonDTO;
import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Application.Mappers.HackathonMapper;
import it.unicam.cs.hackhub.Application.Services.HackathonService;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
import it.unicam.cs.hackhub.Model.Enums.State;
import it.unicam.cs.hackhub.Patterns.Facade.Facade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hackathon")
public class HackathonController {

    private final HackathonService service;
    private final HackathonMapper mapper;
    private final Facade facade;

    public HackathonController(HackathonService service, Facade facade,  HackathonMapper mapper) {
        this.service = service;
        this.facade = facade;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public HackathonDTO createHackathon(
            @RequestBody @Valid CreateHackathonRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {
        return mapper.toDTO(service.createHackathon(req, userDetails));
    }

    @GetMapping("get/registration")
    public List<HackathonDTO> getHackathonsInSubscription() {
        return service.getByState(State.REGISTRATION)
                .stream()
                .map(mapper::toDTO)
                .toList();
        // TODO: aggiungere controllo sul team
    }

    @GetMapping("/consultation")
    public List<HackathonDTO> getAllHackathon() {
        return service.getAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @GetMapping("/concluded")
    public List<ConcludedHackathonDTO> getAllConcluded() {
        return service.getAllConcluded()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<String> registerTeam(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.registerTeam(id, userDetails.getUsername());
        return new ResponseEntity<>("Team correttamente iscritto", HttpStatus.OK);
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
