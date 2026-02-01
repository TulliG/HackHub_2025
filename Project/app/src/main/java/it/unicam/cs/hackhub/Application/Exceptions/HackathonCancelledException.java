package it.unicam.cs.hackhub.Application.Exceptions;

public class HackathonCancelledException extends RuntimeException {
    public HackathonCancelledException(String message) {
        super(message);
    }
}
