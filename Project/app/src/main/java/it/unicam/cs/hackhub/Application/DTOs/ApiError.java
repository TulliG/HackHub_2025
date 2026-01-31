package it.unicam.cs.hackhub.Application.DTOs;

public record ApiError(
        int status,
        String error,
        String message,
        String path
) {}
