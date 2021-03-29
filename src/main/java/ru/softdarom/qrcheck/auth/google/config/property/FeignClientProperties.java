package ru.softdarom.qrcheck.auth.google.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ConfigurationProperties(prefix = "outbound.feign.auth-handler")
public class FeignClientProperties {

    @Pattern(regexp = "^(http)(s?)$")
    private String schema;

    @NotEmpty
    private String host;

    @NotNull
    private Integer port;

    @NotEmpty
    private String path;
}