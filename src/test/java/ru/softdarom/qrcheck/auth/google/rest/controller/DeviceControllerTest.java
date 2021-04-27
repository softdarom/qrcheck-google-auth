package ru.softdarom.qrcheck.auth.google.rest.controller;

import feign.FeignException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2DeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2UpdateDeviceRequest;
import ru.softdarom.qrcheck.auth.google.service.UserHandlerService;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2DeviceRequest;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2UpdateDeviceRequest;

@SpringIntegrationTest
@DisplayName("DeviceController Spring Integration Test")
class DeviceControllerTest extends AbstractControllerTest {

    private static final String URI_SAVE_DEVICE = "/google/devices";
    private static final String URI_UPDATE_DEVICE = "/google/devices";

    @Mock
    private UserHandlerService userHandlerServiceMock;

    @Autowired
    private DeviceController controller;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(controller, "userHandlerService", userHandlerServiceMock);
    }

    @AfterEach
    void tearDown() {
        reset(userHandlerServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("saveDevice(): returns 200 when a device is saved")
    void successfulSaveDevice() {
        doNothing().when(userHandlerServiceMock).saveUserDevice(any(OAuth2DeviceRequest.class));
        var actual = assertDoesNotThrow(() -> post(oAuth2DeviceRequest(), URI_SAVE_DEVICE));
        assertCall().accept(actual, HttpStatus.OK);
        verify(userHandlerServiceMock).saveUserDevice(any(OAuth2DeviceRequest.class));
    }

    @Test
    @DisplayName("updateDevice(): returns 200 when a device is updated")
    void successfulUpdateDevice() {
        doNothing().when(userHandlerServiceMock).updateUserDevice(any(OAuth2UpdateDeviceRequest.class));
        var actual = assertDoesNotThrow(() -> put(oAuth2UpdateDeviceRequest(), URI_UPDATE_DEVICE));
        assertCall().accept(actual, HttpStatus.OK);
        verify(userHandlerServiceMock).updateUserDevice(any(OAuth2UpdateDeviceRequest.class));
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("saveDevice(): returns 500 when an unknown exception from an external service")
    void failSaveDeviceExternalUnknownException() {
        doThrow(FeignException.InternalServerError.class).when(userHandlerServiceMock).saveUserDevice(any(OAuth2DeviceRequest.class));
        var actual = assertDoesNotThrow(() -> post(oAuth2DeviceRequest(), URI_SAVE_DEVICE));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(userHandlerServiceMock).saveUserDevice(any(OAuth2DeviceRequest.class));
    }

    @Test
    @DisplayName("saveDevice(): returns 500 when an unknown exception")
    void failSaveDeviceUnknownException() {
        doThrow(RuntimeException.class).when(userHandlerServiceMock).saveUserDevice(any(OAuth2DeviceRequest.class));
        var actual = assertDoesNotThrow(() -> post(oAuth2DeviceRequest(), URI_SAVE_DEVICE));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(userHandlerServiceMock).saveUserDevice(any(OAuth2DeviceRequest.class));
    }

    @Test
    @DisplayName("updateDevice(): returns 404 when an user or a device not found")
    void failUpdateDeviceExternalNotFoundException() {
        doThrow(FeignException.NotFound.class).when(userHandlerServiceMock).updateUserDevice(any(OAuth2UpdateDeviceRequest.class));
        var actual = assertDoesNotThrow(() -> put(oAuth2UpdateDeviceRequest(), URI_UPDATE_DEVICE));
        assertCall().accept(actual, HttpStatus.NOT_FOUND);
        verify(userHandlerServiceMock).updateUserDevice(any(OAuth2UpdateDeviceRequest.class));
    }

    @Test
    @DisplayName("updateDevice(): returns 500 when an unknown exception from an external service")
    void failUpdateDeviceExternalUnknownException() {
        doThrow(FeignException.InternalServerError.class).when(userHandlerServiceMock).updateUserDevice(any(OAuth2UpdateDeviceRequest.class));
        var actual = assertDoesNotThrow(() -> put(oAuth2UpdateDeviceRequest(), URI_UPDATE_DEVICE));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(userHandlerServiceMock).updateUserDevice(any(OAuth2UpdateDeviceRequest.class));
    }

    @Test
    @DisplayName("updateDevice(): returns 500 when an unknown exception")
    void failUpdateDeviceUnknownException() {
        doThrow(RuntimeException.class).when(userHandlerServiceMock).updateUserDevice(any(OAuth2UpdateDeviceRequest.class));
        var actual = assertDoesNotThrow(() -> put(oAuth2UpdateDeviceRequest(), URI_UPDATE_DEVICE));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(userHandlerServiceMock).updateUserDevice(any(OAuth2UpdateDeviceRequest.class));
    }
}