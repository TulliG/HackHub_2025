package it.unicam.cs.hackhub.Repository;

import java.util.HashMap;
import java.util.Map;

import it.unicam.cs.hackhub.Model.Entity.Appointment;

public class AppointmentRepository {

    private static final Map<Long, Appointment> repo = new HashMap<>();
    private static Long serialId = 1L;

    public Appointment get(Long id) {
        return repo.get(id);
    }

    public void put(Appointment appointment) {
        if (appointment.getId() == null) appointment.setId(serialId++);
        repo.put(appointment.getId(), appointment);
    }

    public void remove(Long id) {
        repo.remove(id);
    }
}
