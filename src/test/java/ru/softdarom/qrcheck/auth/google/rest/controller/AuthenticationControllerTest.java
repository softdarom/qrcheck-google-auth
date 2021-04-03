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
import ru.softdarom.qrcheck.auth.google.service.UserHandlerService;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringIntegrationTest
@DisplayName("AuthenticationController Spring Integration Test")
class AuthenticationControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/oauth2";
    private static final String URI_EXIST_USER = BASE_URL + "/users/test@email.ru/exist";

    @Mock
    private UserHandlerService userHandlerServiceMock;

    @Autowired
    private AuthenticationController controller;

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
    @DisplayName("exist(): returns 200 when an user found")
    void successfulExist() {
        doNothing().when(userHandlerServiceMock).exist(anyString());
        var actual = assertDoesNotThrow(() -> get(Void.class, URI_EXIST_USER));
        assertCall().accept(actual, HttpStatus.OK);
        verify(userHandlerServiceMock).exist(anyString());
    }

    //  -----------------------   fail tests   -------------------------


    @Test
    @DisplayName("exist(): returns 404 when an user not found")
    void failExistNotFound() {
        doThrow(FeignException.NotFound.class).when(userHandlerServiceMock).exist(anyString());
        var actual = assertDoesNotThrow(() -> get(Void.class, URI_EXIST_USER));
        assertCall().accept(actual, HttpStatus.NOT_FOUND);
        verify(userHandlerServiceMock).exist(anyString());
    }

    @Test
    @DisplayName("exist(): returns 500 when an unknown exception from an external service")
    void failExistExternalUnknownException() {
        doThrow(FeignException.InternalServerError.class).when(userHandlerServiceMock).exist(anyString());
        var actual = assertDoesNotThrow(() -> get(Void.class, URI_EXIST_USER));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(userHandlerServiceMock).exist(anyString());
    }

    @Test
    @DisplayName("exist(): returns 500 when an unknown exception")
    void failExistUnknownException() {
        doThrow(RuntimeException.class).when(userHandlerServiceMock).exist(anyString());
        var actual = assertDoesNotThrow(() -> get(Void.class, URI_EXIST_USER));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(userHandlerServiceMock).exist(anyString());
    }
}