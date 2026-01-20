package it.unicam.cs.hackhub.Model.Exceptions;

public class DomainException extends RuntimeException{
    public DomainException(String message) {
        super(message);
    }
}
