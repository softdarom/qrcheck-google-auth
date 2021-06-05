package ru.softdarom.qrcheck.auth.google.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import ru.softdarom.qrcheck.auth.google.client.GoogleClient;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateString;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.googleAccessTokenResponse;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.googleTokenInfoResponse;

@SpringIntegrationTest
@DisplayName("GoogleService Spring Integration Test")
class GoogleServiceTest {

    @Mock
    private GoogleClient googleClientMock;

    @Autowired
    private GoogleService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "googleClient", googleClientMock);
    }

    @AfterEach
    void tearDown() {
        reset(googleClientMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("tokenInfo(): returns GoogleTokenInfoResponse")
    void successfulTokenInfo() {
        when(googleClientMock.tokenInfo(anyString())).thenReturn(ResponseEntity.ok(googleTokenInfoResponse()));
        var actual = assertDoesNotThrow(() -> service.tokenInfo(generateString()));
        assertNotNull(actual);
        verify(googleClientMock).tokenInfo(anyString());
    }

    @Test
    @DisplayName("refresh(): returns GoogleAccessTokenResponse")
    void successfulRefresh() {
        when(googleClientMock.refresh(any())).thenReturn(ResponseEntity.ok(googleAccessTokenResponse()));
        var actual = assertDoesNotThrow(() -> service.refresh(generateString()));
        assertNotNull(actual);
        verify(googleClientMock).refresh(any());
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("tokenInfo(): throws IllegalArgumentException when an accessToken is null")
    void failureTokenInfoNullAccessToken() {
        assertThrows(IllegalArgumentException.class, () -> service.tokenInfo(null));
    }

    @Test
    @DisplayName("tokenInfo(): throws IllegalArgumentException when an accessToken is empty")
    void failureTokenInfoEmptyAccessToken() {
        assertThrows(IllegalArgumentException.class, () -> service.tokenInfo(""));
    }

    @Test
    @DisplayName("refresh(): throws IllegalArgumentException when an accessToken is null")
    void failureRefreshNullRefreshToken() {
        assertThrows(IllegalArgumentException.class, () -> service.refresh(null));
    }

    @Test
    @DisplayName("refresh(): throws IllegalArgumentException when an accessToken is empty")
    void failureRefreshEmptyRefreshToken() {
        assertThrows(IllegalArgumentException.class, () -> service.refresh(""));
    }
}