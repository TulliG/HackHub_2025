package it.unicam.cs.hackhub.Application.Mappers;

import it.unicam.cs.hackhub.Application.DTOs.TeamDTO;
import it.unicam.cs.hackhub.Model.Entities.Team;
import it.unicam.cs.hackhub.Model.Entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TeamMapper {

    public TeamDTO toDTO(Team team) {
        return new TeamDTO(
                team.getId(),
                team.getName(),
                team.getMembers()
                        .stream()
                        .map(User::getUsername)
                        .collect(Collectors.toSet()));
    }
}
