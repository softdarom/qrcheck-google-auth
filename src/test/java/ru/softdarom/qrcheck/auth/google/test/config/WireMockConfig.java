package ru.softdarom.qrcheck.auth.google.test.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockBooksService(FeignAuthHandlerProperties properties) {
        return new WireMockServer(properties.getPort());
    }

    @TestConfiguration
    @ConfigurationProperties(prefix = "outbound.test.auth-handler")
    @Getter
    @Setter
    public static class FeignAuthHandlerProperties {

        private int port;

    }

}