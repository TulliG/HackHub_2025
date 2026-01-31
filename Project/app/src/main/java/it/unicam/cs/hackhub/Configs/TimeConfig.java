package it.unicam.cs.hackhub.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class TimeConfig {

    @Bean
    public Clock clock(
            @Value("${app.time.mode:system}") String mode,
            @Value("${app.time.fixed:}") String fixedTime
    ) {
        ZoneId zone = ZoneId.systemDefault();

        if ("fixed".equalsIgnoreCase(mode)) {
            if (fixedTime == null || fixedTime.isBlank()) {
                throw new IllegalArgumentException(
                        "app.time.mode=fixed requires app.time.fixed=yyyy-MM-ddTHH:mm:ss"
                );
            }
            LocalDateTime ldt = LocalDateTime.parse(fixedTime);
            Instant instant = ldt.atZone(zone).toInstant();
            return Clock.fixed(instant, zone);
        }

        return Clock.system(zone);
    }
}
