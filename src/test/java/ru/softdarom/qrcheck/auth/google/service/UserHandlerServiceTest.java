package ru.softdarom.qrcheck.auth.google.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.softdarom.qrcheck.auth.google.builder.GoogleTokenBuilder;
import ru.softdarom.qrcheck.auth.google.builder.GoogleUserBuilder;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;
import ru.softdarom.qrcheck.auth.google.service.impl.UserHandlerServiceImpl;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateString;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2User;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2UserRequest;

@SpringIntegrationTest
@DisplayName("UserHandlerService Spring Integration Test")
class UserHandlerServiceTest {

    @Mock
    private AuthHandlerExternalService authHandlerExternalServiceMock;

    @Autowired
    private UserHandlerServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "authHandlerExternalService", authHandlerExternalServiceMock);
    }

    @AfterEach
    void tearDown() {
        reset(authHandlerExternalServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("saveOrUpdate(): doesn't throw an exception when OK")
    void successfulSaveOrUpdate() {
        doNothing().when(authHandlerExternalServiceMock).save(any(GoogleCredentialRequest.class));
        assertDoesNotThrow(() -> service.saveOrUpdate(new GoogleUserBuilder(oAuth2User()).build(), new GoogleTokenBuilder(oAuth2UserRequest()).build()));
        verify(authHandlerExternalServiceMock).save(any(GoogleCredentialRequest.class));
    }

    @Test
    @DisplayName("exist(): doesn't throw an exception when an user exists")
    void successfulExist() {
        doNothing().when(authHandlerExternalServiceMock).exist(anyString());
        assertDoesNotThrow(() -> service.exist(generateString()));
        verify(authHandlerExternalServiceMock).exist(anyString());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("saveOrUpdate(): throws IllegalArgumentException when an userDto is null")
    void failSaveOrUpdateNullUserDto() {
        assertThrows(IllegalArgumentException.class, () -> service.saveOrUpdate(null, new GoogleTokenBuilder(oAuth2UserRequest()).build()));
        verify(authHandlerExternalServiceMock, never()).save(any(GoogleCredentialRequest.class));
    }

    @Test
    @DisplayName("saveOrUpdate(): throws IllegalArgumentException when an tokenDto is null")
    void failSaveOrUpdateNullTokenDto() {
        assertThrows(IllegalArgumentException.class, () -> service.saveOrUpdate(new GoogleUserBuilder(oAuth2User()).build(), null));
        verify(authHandlerExternalServiceMock, never()).save(any(GoogleCredentialRequest.class));
    }

    @Test
    @DisplayName("exist(): throws IllegalArgumentException when an email is null")
    void failExistEmailNull() {
        assertThrows(IllegalArgumentException.class, () -> service.exist(null));
        verify(authHandlerExternalServiceMock, never()).exist(anyString());
    }

    @Test
    @DisplayName("exist(): throws IllegalArgumentException when an email is empty")
    void failExistEmailEmpty() {
        assertThrows(IllegalArgumentException.class, () -> service.exist(""));
        verify(authHandlerExternalServiceMock, never()).exist(anyString());
    }
}