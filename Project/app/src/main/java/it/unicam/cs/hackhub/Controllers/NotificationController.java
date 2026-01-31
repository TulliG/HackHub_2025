package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.NotificationDTO;
import it.unicam.cs.hackhub.Application.Mappers.NotificationMapper;
import it.unicam.cs.hackhub.Application.Services.NotificationService;
import it.unicam.cs.hackhub.Model.Enums.NotificationType;
import it.unicam.cs.hackhub.Patterns.Facade.Facade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    public final NotificationService notificationService;
    public final NotificationMapper notificationMapper;
    public final Facade  facade;

    public NotificationController(NotificationService notificationService, Facade facade,  NotificationMapper mapper) {
        this.notificationService = notificationService;
        this.facade = facade;
        this.notificationMapper = mapper;
    }

    // TODO: cambiare details con details.getUsername()
    @PostMapping("/send/team/{id}")
    public NotificationDTO sendTeamInvite(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
            ) {
        return facade.sendTeamInvite(id, details);
    }

    @PostMapping("/send/mentor/{id}")
    public NotificationDTO sendMentorInvite(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
    ) {
        return facade.sendMentorInvite(id, details);
    }

    @PostMapping("/send/judge/{id}")
    public NotificationDTO sendJudgeInvite(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
    ) {
        return facade.sendJudgeInvite(id, details);
    }

    public void bookAppointmeent() {
        //TODO implement: add new Appointment in Hackathon, send notification to involved Team
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<String> accept(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
    ) {
        facade.accept(id, details);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Notifica accettata correttamente");
    }

    @GetMapping("/get/invites")
    public List<NotificationDTO> getInvites(
            @AuthenticationPrincipal UserDetails details
    ) {
        return notificationService.getByReceiver(details)
                .stream()
                .map(notificationMapper::toDTO)
                .toList();
    }

    @GetMapping("get/{id}")
    public NotificationDTO getNotificationDetails( @PathVariable Long id ) {
        return notificationMapper.toDTO(notificationService.getById(id));
    }

    @GetMapping("get/all")
    public List<NotificationDTO> getInvite(
            @AuthenticationPrincipal UserDetails details
    ) {
        return notificationService.getByReceiver(details)
                .stream()
                .map(notificationMapper::toDTO)
                .toList();
    }

    public void getReports() {
        //TODO implement: return all Organizer's reports
    }

    public void getSupportRquests() {
        //TODO implement: return all Mentor's support requests
    }
    public void showDetails() {
        //TODO implement: show notification's details
    }


}
