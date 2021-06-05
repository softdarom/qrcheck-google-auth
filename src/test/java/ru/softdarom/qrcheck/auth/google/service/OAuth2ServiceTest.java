package ru.softdarom.qrcheck.auth.google.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.test.util.ReflectionTestUtils;
import ru.softdarom.qrcheck.auth.google.exception.NotAuthenticatedException;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateString;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.*;

@SpringIntegrationTest
@DisplayName("OAuth2Service Spring Integration Test")
class OAuth2ServiceTest {

    @Mock
    private OAuth2AuthorizedClientService authorizedClientServiceMock;

    @Mock
    private AuthHandlerService authHandlerServiceMock;

    @Mock
    private GoogleService googleServiceMock;

    @Autowired
    private OAuth2Service service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "authorizedClientService", authorizedClientServiceMock);
        ReflectionTestUtils.setField(service, "authHandlerService", authHandlerServiceMock);
        ReflectionTestUtils.setField(service, "googleService", googleServiceMock);
    }

    @AfterEach
    void tearDown() {
        reset(authorizedClientServiceMock, authHandlerServiceMock, googleServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("getOAuthClient(): returns OAuth2AuthorizedClient when an authentication is existed")
    void successfulGetOAuthClient() {
        when(authorizedClientServiceMock.loadAuthorizedClient(any(), any())).thenReturn(oAuth2AuthorizedClient());
        var actual = assertDoesNotThrow(() -> service.getOAuthClient(authentication()));
        assertNotNull(actual);
        verify(authorizedClientServiceMock).loadAuthorizedClient(any(), any());
    }

    @Test
    @DisplayName("saveOAuthInfo(): returns AuthHandlerUserResponse")
    void successfulSaveOAuthInfo() {
        when(authHandlerServiceMock.saveOAuthInfo(any(), any())).thenReturn(authHandlerUserResponse());
        var actual = assertDoesNotThrow(() -> service.saveOAuthInfo(authentication()));
        assertAll(() -> {
            assertNotNull(actual);
            assertNotNull(actual.getUser());
            assertNotNull(actual.getToken());
        });
        verify(authHandlerServiceMock).saveOAuthInfo(any(), any());
    }

    @Test
    @DisplayName("tokenInfo(): returns GoogleTokenInfoResponse")
    void successfulTokenInfo() {
        when(googleServiceMock.tokenInfo(anyString())).thenReturn(googleTokenInfoResponse());
        var actual = assertDoesNotThrow(() -> service.tokenInfo(generateString()));
        assertNotNull(actual);
        verify(googleServiceMock).tokenInfo(anyString());
    }

    @Test
    @DisplayName("refresh(): returns GoogleAccessTokenResponse")
    void successfulRefresh() {
        when(googleServiceMock.refresh(anyString())).thenReturn(googleAccessTokenResponse());
        var actual = assertDoesNotThrow(() -> service.refresh(generateString()));
        assertNotNull(actual);
        verify(googleServiceMock).refresh(anyString());
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("getOAuthClient(): throws NotAuthenticatedException when an authentication isn't existed")
    void failureGetOAuthNotAuthentication() {
        assertThrows(NotAuthenticatedException.class, () -> service.getOAuthClient(null));
        verify(authorizedClientServiceMock, never()).loadAuthorizedClient(any(), any());
    }

    @Test
    @DisplayName("saveOAuthInfo(): throws NotAuthenticatedException when an authentication isn't existed")
    void failureSaveOAuthInfoNotAuthentication() {
        assertThrows(NotAuthenticatedException.class, () -> service.saveOAuthInfo(null));
        verify(authHandlerServiceMock, never()).saveOAuthInfo(any(), any());
    }

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