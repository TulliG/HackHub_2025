package it.unicam.cs.hackhub.Controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    public void send() {
        //TODO implement: send notification to a User(possible overloading?)
        //TODO implement: send support request and report
    }

    public void bookAppointmeent() {
        //TODO implement: add new Appointment in Hackathon, send notification to involved Team
    }

    public void accept() {
        //TODO implement: call facade accept method with involved notification
    }

    public void getAllInvites() {
        //TODO implement: return all notication with notification.getType().isInvite
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
