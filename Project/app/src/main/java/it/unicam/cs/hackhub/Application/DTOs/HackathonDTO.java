package it.unicam.cs.hackhub.Application.DTOs;

import it.unicam.cs.hackhub.Model.Enums.State;

import java.time.LocalDateTime;

public record HackathonDTO(
        Long id,
        String name,
        String location,
        String rules,
        int prize,
        State state,
        LocalDateTime startDate,
        LocalDateTime endingDate,
        String organizerUsername) {}
