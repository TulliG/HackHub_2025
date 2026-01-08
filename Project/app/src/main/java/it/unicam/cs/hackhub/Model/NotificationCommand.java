package it.unicam.cs.hackhub.Model;

/**
 * Interface that defines the command pattern for a {@code Notification}.
 */
public interface NotificationCommand {
    
    /**
     * Method that execute the command;
     */
    public void execute();

}
