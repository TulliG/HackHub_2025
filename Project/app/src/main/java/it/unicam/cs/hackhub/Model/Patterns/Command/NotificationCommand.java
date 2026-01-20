package it.unicam.cs.hackhub.Model.Patterns.Command;

import it.unicam.cs.hackhub.Service.NotificationService;

/**
 * Interface that defines the command pattern for a {@code Notification}.
 */
public interface NotificationCommand {
    /**
     * Method that execute the command;
     */
    void execute();

}
