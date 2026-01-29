package it.unicam.cs.hackhub.Application.DTOs;

import java.time.LocalDateTime;

public record HackathonDTO(
        String name,
        LocalDateTime startDate,
        LocalDateTime endingDate,
        String organizerUsername) {}
