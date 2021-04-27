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
import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateLong;

@SpringIntegrationTest
@DisplayName("UserController Spring Integration Test")
class UserControllerTest extends AbstractControllerTest {

    private static final String TEST_EMAIL = "test@email.ru";
    private static final String URI_EXIST_USER = "/google/users?email=" + TEST_EMAIL;

    @Mock
    private UserHandlerService userHandlerServiceMock;

    @Autowired
    private UserController controller;

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
    @DisplayName("existUser(): returns 200 and user id when an user found")
    void successfulExistUser() {
        when(userHandlerServiceMock.exist(TEST_EMAIL)).thenReturn(generateLong());
        var actual = assertDoesNotThrow(() -> get(Long.class, URI_EXIST_USER));
        assertCallWithBody().accept(actual, HttpStatus.OK);
        verify(userHandlerServiceMock).exist(anyString());
    }

    //  -----------------------   fail tests   -------------------------

    @Test
    @DisplayName("existUser(): returns 404 when an user not found")
    void failExistUserNotFound() {
        when(userHandlerServiceMock.exist(anyString())).thenThrow(FeignException.NotFound.class);
        var actual = assertDoesNotThrow(() -> get(Long.class, URI_EXIST_USER));
        assertCall().accept(actual, HttpStatus.NOT_FOUND);
        verify(userHandlerServiceMock).exist(anyString());
    }

    @Test
    @DisplayName("existUser(): returns 500 when an unknown exception from an external service")
    void failExistUserExternalUnknownException() {
        when(userHandlerServiceMock.exist(anyString())).thenThrow(FeignException.InternalServerError.class);
        var actual = assertDoesNotThrow(() -> get(Long.class, URI_EXIST_USER));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(userHandlerServiceMock).exist(anyString());
    }

    @Test
    @DisplayName("existUser(): returns 500 when an unknown exception")
    void failExistUserUnknownException() {
        when(userHandlerServiceMock.exist(anyString())).thenThrow(RuntimeException.class);
        var actual = assertDoesNotThrow(() -> get(Long.class, URI_EXIST_USER));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        verify(userHandlerServiceMock).exist(anyString());
    }
}