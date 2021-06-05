package ru.softdarom.qrcheck.auth.google.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateString;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.authHandlerTokenUserInfoRequest;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.authHandlerUserResponse;

@SpringIntegrationTest
@DisplayName("AuthHandlerClient Spring Integration Test")
class AuthHandlerClientTest {

    private static final String POST_SAVE_USER_TOKEN_INFO = "/tokens/info";

    @Autowired
    private WireMockServer wireAuthHandlerServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthHandlerClient client;

    @AfterEach
    void tearDown() {
        wireAuthHandlerServiceMock.resetAll();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("saveOAuth2Info(): returns 200 when a user info is saved")
    void successfulSaveOAuth2Info() throws JsonProcessingException {
        var request = authHandlerTokenUserInfoRequest();
        wireAuthHandlerServiceMock
                .stubFor(post(urlEqualTo(POST_SAVE_USER_TOKEN_INFO))
                        .withRequestBody(containing(objectMapper.writeValueAsString(request)))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(objectMapper.writeValueAsString(authHandlerUserResponse()))
                        )

                );
        assertDoesNotThrow(() -> client.saveOAuth2Info(generateString(), request));
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("saveOAuth2Info(): returns 401 when unauthorized calls")
    void failureSaveOAuth2InfoUnauthorized() throws JsonProcessingException {
        var request = authHandlerTokenUserInfoRequest();
        wireAuthHandlerServiceMock
                .stubFor(post(urlEqualTo(POST_SAVE_USER_TOKEN_INFO))
                        .withRequestBody(containing(objectMapper.writeValueAsString(request)))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.UNAUTHORIZED.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        )

                );
        assertThrows(FeignException.Unauthorized.class, () -> client.saveOAuth2Info(generateString(), request));
    }

    @Test
    @DisplayName("saveOAuth2Info(): returns 500 when a unknown exception occurs")
    void failureSaveOAuth2InfoInternalServerError() throws JsonProcessingException {
        var request = authHandlerTokenUserInfoRequest();
        wireAuthHandlerServiceMock
                .stubFor(post(urlEqualTo(POST_SAVE_USER_TOKEN_INFO))
                        .withRequestBody(containing(objectMapper.writeValueAsString(request)))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        )

                );
        assertThrows(FeignException.InternalServerError.class, () -> client.saveOAuth2Info(generateString(), request));
    }
}