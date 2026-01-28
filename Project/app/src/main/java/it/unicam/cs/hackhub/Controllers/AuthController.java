package it.unicam.cs.hackhub.Controllers;

import it.unicam.cs.hackhub.Controllers.Requests.RegisterRequest;
import it.unicam.cs.hackhub.Services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest req) {
        userService.create(req.username(), req.password());
    }
}
