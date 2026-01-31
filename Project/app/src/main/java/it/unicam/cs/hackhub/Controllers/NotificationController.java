package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.NotificationDTO;
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
    public final Facade  facade;

    public NotificationController(NotificationService notificationService, Facade facade) {
        this.notificationService = notificationService;
        this.facade = facade;
    }


    @PostMapping("/send/team/{id}")
    public NotificationDTO send(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
            ) {
        //TODO implement: send notification to a User(possible overloading?)
        //TODO implement: send support request and report
        return facade.sendTeamInvite(id, details);
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
        return notificationService.getByReceiver(details, NotificationType.TEAM_INVITE);
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
