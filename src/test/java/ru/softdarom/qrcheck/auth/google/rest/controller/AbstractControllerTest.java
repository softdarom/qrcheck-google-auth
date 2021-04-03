package ru.softdarom.qrcheck.auth.google.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.softdarom.qrcheck.auth.google.test.helper.UriHelper.generateUri;

abstract class AbstractControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected <T> ResponseEntity<T> get(Class<T> response, String path) {
        var uri = generateUri(path, port);
        return restTemplate.getForEntity(uri, response);
    }

    protected BiConsumer<ResponseEntity<?>, HttpStatus> assertCall() {
        return (response, status) -> {
            assertNotNull(response);
            assertEquals(status, response.getStatusCode());
        };
    }
}