package it.unicam.cs.hackhub.Application.DTOs;

import java.time.LocalDateTime;

public record ConcludedHackathonDTO(
        String name,
        LocalDateTime startDate,
        LocalDateTime endingDate,
        String winnerTeamUsername) {}
