package ru.softdarom.qrcheck.auth.google.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringIntegrationTest
@DisplayName("AuthHandlerExternalService Spring Integration Test")
class AuthHandlerExternalServiceTest {

    private static final String TEST_EMAIL = "test%40email.ru";
    private static final String BASE_URL = "/google";
    private static final String EXIST_USER = BASE_URL + "/users/" + TEST_EMAIL;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private AuthHandlerExternalService service;

    @BeforeEach
    void setUp() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo(EXIST_USER))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("exist(): returns true when an user exists")
    void successfulExist() {
        assertDoesNotThrow(() -> service.exist(TEST_EMAIL));
    }
}