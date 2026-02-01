package it.unicam.cs.hackhub.Controllers.Requests;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateHackathonRequest(

        @NotBlank String name,
        @NotBlank String location,
        @NotBlank String rules,
        @Positive int prize,
        @NotNull  LocalDateTime startDate,
        @NotNull LocalDateTime evaluationDate,
        @NotNull LocalDateTime endingDate) {}
