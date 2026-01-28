package it.unicam.cs.hackhub.Services;

import it.unicam.cs.hackhub.Model.Entities.User;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

    private final UserService userService;

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userService.getByUsername(username)
                .map(u -> {
                    return org.springframework.security.core.userdetails.User
                            .withUsername(u.getUsername())
                            .password(u.getPassword())
                            .roles("USER")
                            .build();
                })
                .orElseThrow(() -> {
                    log.warn("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found");
                });
    }
}
