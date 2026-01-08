package it.unicam.cs.hackhub.Model;

/**
 * Class that represents the notification inside the system.
 */
public class Notification {
    
    private Long id;

    private User sender;

    private User receiver;

    private String message;

    private NotificationType type;

    private Long targetId;

    public Notification() {}

}
