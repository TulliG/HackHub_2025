package it.unicam.cs.hackhub.Application.Mappers;

import it.unicam.cs.hackhub.Application.DTOs.ConcludedHackathonDTO;
import it.unicam.cs.hackhub.Application.DTOs.HackathonDTO;
import it.unicam.cs.hackhub.Model.Entities.ConcludedHackathon;
import it.unicam.cs.hackhub.Model.Entities.Hackathon;
import it.unicam.cs.hackhub.Model.Enums.Role;
import org.springframework.stereotype.Component;

@Component
public class HackathonMapper {

    public HackathonDTO toDTO(Hackathon hackathon) {
        String organizer = hackathon
                .getParticipations()
                .stream()
                .filter(p -> p.getRole() == Role.ORGANIZER)
                .map(p -> p.getUser().getUsername())
                .findFirst()
                .orElse(null);
        return new HackathonDTO(hackathon.getName(), hackathon.getLocation(), hackathon.getRules(), hackathon.getPrize(),
                hackathon.getState(), hackathon.getStartDate(), hackathon.getEndingDate(), organizer);
    }

    public ConcludedHackathonDTO toDTO(ConcludedHackathon hackathon) {
        return new ConcludedHackathonDTO(hackathon.getName(),
                hackathon.getStartDate(),
                hackathon.getEndingDate(),
                hackathon.getWinnerTeamName());
    }

}
