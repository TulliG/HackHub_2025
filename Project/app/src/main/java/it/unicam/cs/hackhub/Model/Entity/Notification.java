package it.unicam.cs.hackhub.Model.Entity;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unicam.cs.hackhub.Model.Enums.NotificationType;

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

    /**
     * Creates a {@code Notification}.
     *
     * @param sender the sender
     * @param receiver the receiver
     * @param message the body
     * @param type the type of {@code Notification}
     * @param targetId the targeted element's id
     */
    public Notification(@NonNull User sender,
                        @NonNull User receiver,
                        @NonNull String message,
                        @NonNull NotificationType type,
                        @NonNull Long targetId) {
        if (type != NotificationType.INFO && sender == receiver)
            throw new IllegalArgumentException("Non informational notification cannot be self sended");
        this.sender = sender;
        this.receiver = receiver;
        this.message = message.trim();
        this.type = type;
        this.targetId = targetId;
    }

    public Notification(@NonNull User sender,@NonNull User receiver,@NonNull String message) {
        if (message.trim().isEmpty())
            throw new IllegalArgumentException("informational notification must have a message");
        this.sender = sender;
        this.receiver = receiver;
        this.message = message.trim();
        this.type = NotificationType.INFO;
    }

    public Long getId() {
        return id;
    }

    /**
     * @return the sender
     */
    public User getSender() {
        return sender;
    }

    /**
     * @return the receiver
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * @return the body of the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the type of the {@code Notification}
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * @return the targeted element's id
     */
    public Long getTargetId() {
        return targetId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}