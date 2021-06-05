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
import ru.softdarom.qrcheck.auth.google.client.AuthHandlerClient;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.*;

@SpringIntegrationTest
@DisplayName("AuthHandlerService Spring Integration Test")
class AuthHandlerServiceTest {

    @Mock
    private AuthHandlerClient authHandlerClientMock;

    @Autowired
    private AuthHandlerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "authHandlerClient", authHandlerClientMock);
    }

    @AfterEach
    void tearDown() {
        reset(authHandlerClientMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("saveOAuthInfo(): returns AuthHandlerUserResponse")
    void successfulSaveOAuthInfo() {
        when(authHandlerClientMock.saveOAuth2Info(anyString(), any())).thenReturn(ResponseEntity.ok(authHandlerUserResponse()));
        var actual = assertDoesNotThrow(() -> service.saveOAuthInfo(oAuth2User(), oAuth2AuthorizedClient()));
        assertNotNull(actual);
        verify(authHandlerClientMock).saveOAuth2Info(anyString(), any());
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("saveOAuthInfo(): throws IllegalArgumentException when oAuth2User is null")
    void failureSaveOAuthInfoNullOAuth2User() {
        assertThrows(IllegalArgumentException.class, () -> service.saveOAuthInfo(null, oAuth2AuthorizedClient()));
        verify(authHandlerClientMock, never()).saveOAuth2Info(anyString(), any());
    }

    @Test
    @DisplayName("saveOAuthInfo(): throws IllegalArgumentException when oAuth2User is null")
    void failureSaveOAuthInfoNullOAuth2AuthorizedClient() {
        assertThrows(IllegalArgumentException.class, () -> service.saveOAuthInfo(oAuth2User(), null));
        verify(authHandlerClientMock, never()).saveOAuth2Info(anyString(), any());
    }
}