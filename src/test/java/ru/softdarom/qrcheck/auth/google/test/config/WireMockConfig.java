package ru.softdarom.qrcheck.auth.google.test.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireAuthHandlerServiceMock(@Value("${outbound.feign.auth-handler.port}") Integer port) {
        return new WireMockServer(port);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireGoogleServiceMock(@Value("${spring.security.oauth2.client.registration.google.api.port}") Integer port) {
        return new WireMockServer(port);
    }
}