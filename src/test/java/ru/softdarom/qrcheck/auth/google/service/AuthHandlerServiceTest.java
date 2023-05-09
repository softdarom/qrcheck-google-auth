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
import ru.softdarom.security.oauth2.service.AuthExternalService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.*;

@SpringIntegrationTest
@DisplayName("AuthHandlerService Spring Integration Test")
class AuthHandlerServiceTest {

    @Mock
    private AuthHandlerClient authHandlerClientMock;

    @Mock
    private  AuthExternalService authExternalServiceMock;

    @Autowired
    private AuthHandlerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "authHandlerClient", authHandlerClientMock);
        ReflectionTestUtils.setField(service, "authExternalService", authExternalServiceMock);
    }

    @AfterEach
    void tearDown() {
        reset(authHandlerClientMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("saveOAuthInfo(): returns AuthHandlerUserResponse")
    void successfulSaveOAuthInfo() {
        when(authExternalServiceMock.getOutgoingToken(anyString())).thenReturn(UUID.randomUUID());
        when(authHandlerClientMock.saveOAuth2Info(any(), any())).thenReturn(ResponseEntity.ok(authHandlerUserResponse()));
        var actual = assertDoesNotThrow(() -> service.saveOAuthInfo(oAuth2User(), oAuth2AuthorizedClient()));
        assertNotNull(actual);
        verify(authExternalServiceMock).getOutgoingToken(anyString());
        verify(authHandlerClientMock).saveOAuth2Info(any(), any());
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("saveOAuthInfo(): throws IllegalArgumentException when oAuth2User is null")
    void failureSaveOAuthInfoNullOAuth2User() {
        var oAuth2AuthorizedClient = oAuth2AuthorizedClient();
        assertThrows(IllegalArgumentException.class, () -> service.saveOAuthInfo(null, oAuth2AuthorizedClient));
        verify(authHandlerClientMock, never()).saveOAuth2Info(any(), any());
    }

    @Test
    @DisplayName("saveOAuthInfo(): throws IllegalArgumentException when oAuth2User is null")
    void failureSaveOAuthInfoNullOAuth2AuthorizedClient() {
        var oAuth2User = oAuth2User();
        assertThrows(IllegalArgumentException.class, () -> service.saveOAuthInfo(oAuth2User, null));
        verify(authHandlerClientMock, never()).saveOAuth2Info(any(), any());
    }
}