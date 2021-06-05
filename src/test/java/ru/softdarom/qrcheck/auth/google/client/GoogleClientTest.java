package ru.softdarom.qrcheck.auth.google.client;

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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateString;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.*;

@SpringIntegrationTest
@DisplayName("AuthHandlerClient Spring Integration Test")
class GoogleClientTest {

    private static final String GET_TOKEN_INFO = "/tokeninfo";
    private static final String POST_REFRESH_TOKEN = "/token";

    @Autowired
    private WireMockServer wireGoogleServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoogleClient client;

    @AfterEach
    void tearDown() {
        wireGoogleServiceMock.resetAll();
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("tokenInfo(): returns 200 when a GoogleTokenInfoResponse is returned")
    void successfulTokenInfo() throws JsonProcessingException {
        var accessToken = generateString();
        wireGoogleServiceMock
                .stubFor(get(urlPathEqualTo(GET_TOKEN_INFO))
                        .withQueryParam("access_token", equalTo(accessToken))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(objectMapper.writeValueAsString(googleTokenInfoResponse()))
                        )

                );
        assertDoesNotThrow(() -> client.tokenInfo(accessToken));
    }

    @Test
    @DisplayName("refresh(): returns 200 when a GoogleAccessTokenResponse is returned")
    void successfulRefresh() throws JsonProcessingException {
        var request = googleRefreshTokenRequest();
        wireGoogleServiceMock
                .stubFor(post(urlEqualTo(POST_REFRESH_TOKEN))
                        .withRequestBody(containing(objectMapper.writeValueAsString(request)))
                        .willReturn(
                                aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(objectMapper.writeValueAsString(googleAccessTokenResponse()))
                        )

                );
        assertDoesNotThrow(() -> client.refresh(request));
    }
}