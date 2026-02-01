package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.NotificationDTO;
import it.unicam.cs.hackhub.Application.Mappers.NotificationMapper;
import it.unicam.cs.hackhub.Application.Services.NotificationService;
import it.unicam.cs.hackhub.Controllers.Requests.AcceptSupportRequest;
import it.unicam.cs.hackhub.Controllers.Requests.ReportRequest;
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

    @PostMapping("/send/team/{id}")
    public NotificationDTO sendTeamInvite(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
            ) {
        return facade.sendTeamInvite(id, details.getUsername());
    }

    @PostMapping("/send/mentor/{id}")
    public NotificationDTO sendMentorInvite(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
    ) {
        return facade.sendMentorInvite(id, details.getUsername());
    }

    @PostMapping("/send/judge/{id}")
    public NotificationDTO sendJudgeInvite(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
    ) {
        return facade.sendJudgeInvite(id, details.getUsername());
    }

    @PostMapping("/send/support/{id}")
    public NotificationDTO sendSupportRequest (
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details
    ) {
        return facade.sendSupportRequest(id, details.getUsername());
    }

    public void bookAppointmeent() {
        //TODO implement: add new Appointment in Hackathon, send notification to involved Team
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<String> accept(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details,
            @RequestBody(required = false) AcceptSupportRequest body
    ) {
        facade.accept(id, details.getUsername(), body);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Notifica accettata correttamente");
    }

    @GetMapping("/get/invites")
    public List<NotificationDTO> getInvites(
            @AuthenticationPrincipal UserDetails details
    ) {
        return notificationService.getInvites(details.getUsername())
                .stream()
                .map(notificationMapper::toDTO)
                .toList();
    }

    @GetMapping("/get/support")
    public List<NotificationDTO> getSupportRequests(
            @AuthenticationPrincipal UserDetails details
    ) {
        return facade.getSupportRequests(
                details.getUsername())
                .stream()
                .map(notificationMapper::toDTO)
                .toList();
    }

    @GetMapping("get/{id}")
    public NotificationDTO getNotificationDetails( @PathVariable Long id ) {
        return notificationMapper.toDTO(notificationService.getById(id));
    }

    @GetMapping("get/all")
    public List<NotificationDTO> getAll(
            @AuthenticationPrincipal UserDetails details
    ) {
        return notificationService.getByReceiver(details.getUsername())
                .stream()
                .map(notificationMapper::toDTO)
                .toList();
    }

    @PostMapping("/send/report/{id}")
    public NotificationDTO sendReport(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails details,
            @RequestBody ReportRequest report
    ) {
        return facade.sendReport(id, details.getUsername(), report.report());
    }

    @GetMapping("/get/reports")
    public List<NotificationDTO> getReports(
            @AuthenticationPrincipal UserDetails details) {
        return facade.getReports(details.getUsername())
                .stream()
                .map(notificationMapper::toDTO)
                .toList();
    }
}
