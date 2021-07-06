package ru.softdarom.qrcheck.auth.google.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {

    private static final String APPLICATION_NAME_TAG = "appliction";

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCustomTags(@Value("${spring.application.name}") String applicationName) {
        return registry -> registry.config().commonTags(APPLICATION_NAME_TAG, applicationName);
    }
}