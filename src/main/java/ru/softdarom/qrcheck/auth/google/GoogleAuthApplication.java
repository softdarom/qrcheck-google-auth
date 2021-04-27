package ru.softdarom.qrcheck.auth.google;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.softdarom.qrcheck.auth.google.config.property.LogbookProperties;

@Generated
@SpringBootApplication
@EnableWebSecurity
@EnableFeignClients
@EnableConfigurationProperties({LogbookProperties.class})
public class GoogleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleAuthApplication.class, args);
    }

}
