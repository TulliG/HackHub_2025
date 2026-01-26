package it.unicam.cs.hackhub.Repositories;

import it.unicam.cs.hackhub.Model.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

}
