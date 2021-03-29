package ru.softdarom.qrcheck.auth.google.config;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;
import ru.softdarom.qrcheck.auth.google.config.property.FeignClientProperties;
import ru.softdarom.qrcheck.auth.google.service.AuthHandlerExternalService;

@Configuration
class FeignClientConfig {

    @Bean
    AuthHandlerExternalService authHandlerExternalService(FeignClientProperties properties) {
        var url =
                UriComponentsBuilder.newInstance()
                        .scheme(properties.getSchema())
                        .host(properties.getHost())
                        .port(properties.getPort())
                        .path(properties.getPath())
                        .build().toUriString();
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger("GOOGLE-AUTH-FEIGN-CLIENT"))
                .logLevel(Logger.Level.FULL)
                .target(AuthHandlerExternalService.class, url);
    }
}