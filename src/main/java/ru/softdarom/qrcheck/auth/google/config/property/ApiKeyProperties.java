package ru.softdarom.qrcheck.auth.google.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Valid
@Getter
@Setter
@ConfigurationProperties(value = "spring.security.api-key")
public class ApiKeyProperties {

    @NotNull
    private Token token;

    @Valid
    @Getter
    @Setter
    public static class Token {

        @NotEmpty
        private String outgoing;

    }

}