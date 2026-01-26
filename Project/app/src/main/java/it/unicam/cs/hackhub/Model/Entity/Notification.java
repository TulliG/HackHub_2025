package it.unicam.cs.hackhub.Model.Entity;

import lombok.NonNull;
import lombok.Getter;

import it.unicam.cs.hackhub.Model.Enums.NotificationType;

/**
 * Class that represents the notification inside the system.
 */
public class Notification {
    
    @Getter
    private Long id;

    @Getter
    private User sender;

    @Getter
    private User receiver;

    @Getter
    private String message;

    @Getter
    private NotificationType type;

    @Getter
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

    public void setId(Long id) {
        this.id = id;
    }
}
