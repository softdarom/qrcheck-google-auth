package ru.softdarom.qrcheck.auth.google;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.softdarom.qrcheck.auth.google.config.property.FeignClientProperties;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties({FeignClientProperties.class})
public class GoogleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleAuthApplication.class, args);
    }

}
