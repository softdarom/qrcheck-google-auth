package ru.softdarom.qrcheck.auth.google.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.*;

@SpringIntegrationTest
@DisplayName("AuthHandlerExternalService Spring Integration Test")
class AuthHandlerExternalServiceTest {

    private static final String TEST_EMAIL = "test%40email.ru";

    private static final String SAVE_USER = "/google/users";
    private static final String GET_USER = "/google/users?email=" + TEST_EMAIL;
    private static final String SAVE_DEVICE = "/google/devices";
    private static final String UPDATE_DEVICE = "/google/devices";

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthHandlerExternalService service;

    @AfterEach
    void tearDown() {
        wireMockServer.resetAll();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("saveUser(): returns 200 when an user is saved")
    void successfulSaveUser() throws JsonProcessingException {
        var request = googleCredentialRequest();
        wireMockServer
                .stubFor(post(urlEqualTo(SAVE_USER))
                        .withRequestBody(containing(objectMapper.writeValueAsString(request)))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        )

                );
        assertDoesNotThrow(() -> service.saveUser(request));
    }

    @Test
    @DisplayName("getUser(): returns user id when an user exists")
    void successfulGetUser() throws JsonProcessingException {
        var expected = googleUserResponse();
        wireMockServer
                .stubFor(get(urlEqualTo(GET_USER))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(objectMapper.writeValueAsString(expected))
                        )

                );
        var actual = assertDoesNotThrow(() -> service.getUser(TEST_EMAIL));
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("saveDevice(): returns 200 when a device is saved")
    void successfulSaveDevice() throws JsonProcessingException {
        var request = oAuth2DeviceRequest();
        wireMockServer
                .stubFor(post(urlEqualTo(SAVE_DEVICE))
                        .withRequestBody(containing(objectMapper.writeValueAsString(request)))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        )

                );
        assertDoesNotThrow(() -> service.saveDevice(request));
    }

    @Test
    @DisplayName("updateDevice(): returns 200 when a device is updated")
    void successfulUpdateDevice() throws JsonProcessingException {
        var request = oAuth2UpdateDeviceRequest();
        wireMockServer
                .stubFor(put(urlEqualTo(UPDATE_DEVICE))
                        .withRequestBody(containing(objectMapper.writeValueAsString(request)))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        )

                );
        assertDoesNotThrow(() -> service.updateDevice(request));
    }
}