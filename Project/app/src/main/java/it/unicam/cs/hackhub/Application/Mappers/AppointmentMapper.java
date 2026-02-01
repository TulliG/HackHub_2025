package it.unicam.cs.hackhub.Application.Mappers;

import it.unicam.cs.hackhub.Application.DTOs.AppointmentDTO;
import it.unicam.cs.hackhub.Model.Entities.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getHackathon().getName(),
                appointment.getTeam().getName(),
                appointment.getDescription()
        );
    }
}
