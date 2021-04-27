package ru.softdarom.qrcheck.auth.google.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2DeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2UpdateDeviceRequest;
import ru.softdarom.qrcheck.auth.google.service.impl.UserHandlerServiceImpl;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.*;

@SpringIntegrationTest
@DisplayName("UserHandlerService Spring Integration Test")
class UserHandlerServiceTest {

    private static final String TEST_EMAIL = "test@email.ru";

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
    @DisplayName("saveUser(): doesn't throw an exception when OK")
    void successfulSaveUser() {
        doNothing().when(authHandlerExternalServiceMock).saveUser(any(GoogleCredentialRequest.class));
        assertDoesNotThrow(() -> service.saveUser(googleUserDto(), googleTokenDto()));
        verify(authHandlerExternalServiceMock).saveUser(any(GoogleCredentialRequest.class));
    }

    @Test
    @DisplayName("saveUserDevice(): doesn't throw an exception when OK")
    void successfulSaveUserDevice() {
        doNothing().when(authHandlerExternalServiceMock).saveDevice(any(OAuth2DeviceRequest.class));
        assertDoesNotThrow(() -> service.saveUserDevice(oAuth2DeviceRequest()));
        verify(authHandlerExternalServiceMock).saveDevice(any(OAuth2DeviceRequest.class));
    }

    @Test
    @DisplayName("updateUserDevice(): doesn't throw an exception when OK")
    void successfulUpdateUserDevice() {
        doNothing().when(authHandlerExternalServiceMock).updateDevice(any(OAuth2UpdateDeviceRequest.class));
        assertDoesNotThrow(() -> service.updateUserDevice(oAuth2UpdateDeviceRequest()));
        verify(authHandlerExternalServiceMock).updateDevice(any(OAuth2UpdateDeviceRequest.class));
    }

    @Test
    @DisplayName("exist(): returns user id when an user exists")
    void successfulExist() {
        var expected = googleUserResponse();
        when(authHandlerExternalServiceMock.getUser(TEST_EMAIL)).thenReturn(expected);
        var actual = assertDoesNotThrow(() -> service.exist(TEST_EMAIL));
        verify(authHandlerExternalServiceMock).getUser(TEST_EMAIL);
        assertEquals(expected.getId(), actual);
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("saveUser(): throws IllegalArgumentException when an userDto is null")
    void failSaveUserNullUserDto() {
        assertThrows(IllegalArgumentException.class, () -> service.saveUser(null, googleTokenDto()));
        verify(authHandlerExternalServiceMock, never()).saveUser(any(GoogleCredentialRequest.class));
    }

    @Test
    @DisplayName("saveDevice(): throws IllegalArgumentException when a tokenDto is null")
    void failSaveUserNullTokenDto() {
        assertThrows(IllegalArgumentException.class, () -> service.saveUser(googleUserDto(), null));
        verify(authHandlerExternalServiceMock, never()).saveUser(any(GoogleCredentialRequest.class));
    }

    @Test
    @DisplayName("saveUser(): throws IllegalArgumentException when a request is null")
    void failSaveDeviceNullRequest() {
        assertThrows(IllegalArgumentException.class, () -> service.saveUserDevice(null));
        verify(authHandlerExternalServiceMock, never()).saveDevice(any(OAuth2DeviceRequest.class));
    }

    @Test
    @DisplayName("updateUserDevice(): throws IllegalArgumentException when a request is null")
    void failUpdateUserDeviceNullTokenDto() {
        assertThrows(IllegalArgumentException.class, () -> service.updateUserDevice(null));
        verify(authHandlerExternalServiceMock, never()).updateDevice(any(OAuth2UpdateDeviceRequest.class));
    }

    @Test
    @DisplayName("exist(): throws IllegalArgumentException when an email is null")
    void failExistEmailNull() {
        assertThrows(IllegalArgumentException.class, () -> service.exist(null));
        verify(authHandlerExternalServiceMock, never()).getUser(anyString());
    }

    @Test
    @DisplayName("exist(): throws IllegalArgumentException when an email is empty")
    void failExistEmailEmpty() {
        assertThrows(IllegalArgumentException.class, () -> service.exist(""));
        verify(authHandlerExternalServiceMock, never()).getUser(anyString());
    }
}