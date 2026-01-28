package it.unicam.cs.hackhub.Model.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.NonNull;
import lombok.Getter;
import jakarta.persistence.*;


import it.unicam.cs.hackhub.Model.Enums.NotificationType;

/**
 * Class that represents the notification inside the system.
 */
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id") // nullable: true (notifiche di sistema)
    private User sender;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Getter
    @Column(nullable = false, length = 1000)
    private String message;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Getter
    @Column(name = "target_id")
    private Long targetId;

    protected Notification() {} // richiesto da JPA

    public Notification(@NonNull User sender,
                        @NonNull User receiver,
                        @NonNull String message,
                        @NonNull NotificationType type,
                        Long targetId) {
        if (message.trim().isEmpty())
            throw new IllegalArgumentException("notification must have a message");
        if (type != NotificationType.INFO && sender == receiver)
            throw new IllegalArgumentException("Non informational notification cannot be self sended");

        this.sender = sender;
        this.receiver = receiver;
        this.message = message.trim();
        this.type = type;
        this.targetId = targetId;
    }

    public Notification(@NonNull User sender,
                        @NonNull User receiver,
                        @NonNull String message) {
        if (message.trim().isEmpty())
            throw new IllegalArgumentException("informational notification must have a message");

        this.sender = sender;
        this.receiver = receiver;
        this.message = message.trim();
        this.type = NotificationType.INFO;
        this.targetId = null;
    }
}
