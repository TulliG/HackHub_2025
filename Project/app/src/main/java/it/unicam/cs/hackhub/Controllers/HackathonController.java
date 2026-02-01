package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.*;
import it.unicam.cs.hackhub.Application.Mappers.HackathonMapper;
import it.unicam.cs.hackhub.Application.Mappers.SubmissionMapper;
import it.unicam.cs.hackhub.Application.Mappers.UserMapper;
import it.unicam.cs.hackhub.Application.Services.HackathonService;
import it.unicam.cs.hackhub.Controllers.Requests.CreateHackathonRequest;
import it.unicam.cs.hackhub.Controllers.Requests.SubmissionRequest;
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
    private final HackathonMapper hackathonMapper;
    private final SubmissionMapper submissionMapper;
    private final Facade facade;
    private final UserMapper userMapper;

    public HackathonController(HackathonService service, Facade facade, HackathonMapper mapper, SubmissionMapper submissionMapper, UserMapper userMapper) {
        this.service = service;
        this.facade = facade;
        this.hackathonMapper = mapper;
        this.submissionMapper = submissionMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public HackathonDTO createHackathon(
            @RequestBody @Valid CreateHackathonRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {
        return hackathonMapper.toDTO(service.createHackathon(req, userDetails));
    }

    @GetMapping("get/registration")
    public List<HackathonDTO> getHackathonsInSubscription() {
        return service.getByState(State.REGISTRATION)
                .stream()
                .map(hackathonMapper::toDTO)
                .toList();
        // TODO: aggiungere controllo sul team
    }

    @GetMapping("/consultation")
    public List<HackathonDTO> getAllHackathon() {
        return service.getAll()
                .stream()
                .map(hackathonMapper::toDTO)
                .toList();
    }

    @GetMapping("/concluded")
    public List<ConcludedHackathonDTO> getAllConcluded() {
        return service.getAllConcluded()
                .stream()
                .map(hackathonMapper::toDTO)
                .toList();
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<String> registerTeam(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        service.registerTeam(id, userDetails.getUsername());
        return new ResponseEntity<>("Team correttamente iscritto", HttpStatus.OK);
    }

    @PostMapping("/unregister")
    public ResponseEntity<String> unregisterTeam(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        service.unregisterTeam(userDetails.getUsername());
        return new ResponseEntity<>("Team correttamente disiscritto", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public SubmissionDTO uploadSubmission(
            @RequestBody @Valid SubmissionRequest req,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        return submissionMapper.toDTO(service.uploadSubmission(req, userDetails.getUsername()));
    }

    public void proclaimWinner() {
        //TODO implement: select Hackathon's winner
    }

    @GetMapping("/get/mentors")
    public List<UserDTO> getMentors(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return service.getMentors(userDetails.getUsername())
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @GetMapping("/get/submissions")
    public List<SubmissionDTO> getSubmissions(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return service.getSubmissions(userDetails.getUsername())
                .stream()
                .map(submissionMapper::toDTO)
                .toList();
    }

    public void rateSubmission() {
        //TODO implement: set Submission' s grade
    }

    @GetMapping("/get/calendar")
    public List<AppointmentDTO> getCalendar(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        //TODO implement: return all Hackathon's Appointments
    }

    public void getHackathonTeams() {
        //TODO implement: return all Hackathon's Teams
    }

    public void sendRequest() {
        //TODO implement: send support request in Hackathon
    }
}
