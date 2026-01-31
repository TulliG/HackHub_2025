package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.UserDTO;
import it.unicam.cs.hackhub.Application.Mappers.UserMapper;
import it.unicam.cs.hackhub.Model.Entities.User;
import it.unicam.cs.hackhub.Application.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        User u = userService.getById(id);
        return userMapper.toDTO(u);
    }

    @GetMapping("/available")
    public void getAvailableUsers() {
        userService.getAvailableUsers()
                .forEach(userMapper::toDTO);
    }


}
