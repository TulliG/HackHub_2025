package it.unicam.cs.hackhub.Controllers.Requests;

import jakarta.validation.constraints.NotBlank;

public record SubmissionRequest(
        @NotBlank String content) {
}
