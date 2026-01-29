package it.unicam.cs.hackhub.Controllers.Requests;

import java.time.LocalDateTime;

public record CreateHackathonRequest(
        String name,
        String location,
        String rules,
        int prize,
        LocalDateTime startDate,
        LocalDateTime evaluationDate,
        LocalDateTime endingDate) {}
