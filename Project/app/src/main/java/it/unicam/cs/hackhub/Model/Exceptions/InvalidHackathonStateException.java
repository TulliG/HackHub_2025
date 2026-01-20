package it.unicam.cs.hackhub.Model.Exceptions;

import it.unicam.cs.hackhub.Model.Patterns.State.HackathonState;

public class InvalidHackathonStateException extends Exception {
    public InvalidHackathonStateException(
            String action,
            HackathonState hackathonState) {
        super("Method " + action + " is not supported in this state " + hackathonState.getName());
    }
}
