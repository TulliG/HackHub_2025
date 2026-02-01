package it.unicam.cs.hackhub.Application.DTOs;

public record SubmissionDTO(
        Long id,
        String content,
        Integer grade,
        String team
) {
}
