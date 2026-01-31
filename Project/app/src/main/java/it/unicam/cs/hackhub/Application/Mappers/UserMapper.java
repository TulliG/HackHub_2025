package it.unicam.cs.hackhub.Application.Mappers;

import it.unicam.cs.hackhub.Application.DTOs.UserDTO;
import it.unicam.cs.hackhub.Model.Entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
}
