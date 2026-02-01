package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Application.DTOs.UserDTO;
import it.unicam.cs.hackhub.Application.Mappers.UserMapper;
import it.unicam.cs.hackhub.Controllers.Requests.RegisterRequest;
import it.unicam.cs.hackhub.Application.Services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterRequest req) {
        return userMapper.toDTO(userService.create(req.username(), req.password()));
    }

}
