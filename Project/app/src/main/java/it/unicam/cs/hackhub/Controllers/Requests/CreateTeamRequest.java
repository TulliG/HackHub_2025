package it.unicam.cs.hackhub.Controllers.Requests;

import jakarta.validation.constraints.NotBlank;

public record CreateTeamRequest(
        @NotBlank String name) {
}
