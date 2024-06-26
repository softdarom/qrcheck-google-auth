package ru.softdarom.qrcheck.auth.google.controller;

import feign.FeignException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import ru.softdarom.qrcheck.auth.google.exception.NotAuthenticatedException;
import ru.softdarom.qrcheck.auth.google.model.dto.response.BaseResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.MobileUserInfoResponse;
import ru.softdarom.qrcheck.auth.google.rest.controller.OAuth2Controller;
import ru.softdarom.qrcheck.auth.google.service.OAuth2Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.authHandlerUserResponse;

@DisplayName("OAuth2Controller Spring Integration Test")
class OAuth2ControllerTest extends AbstractControllerTest {

    private static final String HEADER_ACCESS_TOKEN_NAME = "X-Authorization-Token";

    private static final String URI_SUCCESS = "/oauth2/success";
    private static final String URI_FAILURE = "/oauth2/failure";

    @Mock
    private OAuth2Service auth2ServiceMock;

    @Autowired
    private OAuth2Controller controller;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(controller, "auth2Service", auth2ServiceMock);
    }

    @AfterEach
    void tearDown() {
        reset(auth2ServiceMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("success(): returns 200 when a user is authenticated")
    void successfulSuccess() {
        when(auth2ServiceMock.saveOAuthInfo(any())).thenReturn(authHandlerUserResponse());
        var actual = assertDoesNotThrow(() -> get(MobileUserInfoResponse.class, URI_SUCCESS));
        assertAll(() -> {
            assertCallWithBody().accept(actual, HttpStatus.OK);
            var body = actual.getBody();
            assertNotNull(body.getId());
            assertNotNull(body.getEmail());
            assertNotNull(body.getFirstName());
            assertNotNull(body.getSecondName());
            assertNotNull(body.getAccessToken());
            assertNotNull(body.getAvatar());
        });
        verify(auth2ServiceMock).saveOAuthInfo(any());
    }

    @Test
    @DisplayName("failure(): returns 400 when a user had not authenticated via Google")
    void successfulFailure() {
        var actual = assertDoesNotThrow(() -> get(BaseResponse.class, URI_FAILURE));
        assertAll(() -> {
            assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
            assertNotNull(actual.getBody().getMessage());
        });
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("success(): returns 401 when a user is not authenticated")
    void failureSuccessNotAuthenticatedException() {
        doThrow(NotAuthenticatedException.class).when(auth2ServiceMock).saveOAuthInfo(any());
        var actual = assertDoesNotThrow(() -> get(Void.class, URI_SUCCESS));
        assertCall().accept(actual, HttpStatus.UNAUTHORIZED);
        var actualAccessToken = actual.getHeaders().get(HEADER_ACCESS_TOKEN_NAME);
        assertNull(actualAccessToken);
        verify(auth2ServiceMock).saveOAuthInfo(any());
    }

    @Test
    @DisplayName("success(): returns 401 when the feign client throws Unauthorized")
    void failureSuccessUnauthorizedException() {
        doThrow(FeignException.Unauthorized.class).when(auth2ServiceMock).saveOAuthInfo(any());
        var actual = assertDoesNotThrow(() -> get(Void.class, URI_SUCCESS));
        assertCall().accept(actual, HttpStatus.UNAUTHORIZED);
        var actualAccessToken = actual.getHeaders().get(HEADER_ACCESS_TOKEN_NAME);
        assertNull(actualAccessToken);
        verify(auth2ServiceMock).saveOAuthInfo(any());
    }

    @Test
    @DisplayName("success(): returns 500 when a unknown exception")
    void failureSuccessUnknownException() {
        doThrow(RuntimeException.class).when(auth2ServiceMock).saveOAuthInfo(any());
        var actual = assertDoesNotThrow(() -> get(Void.class, URI_SUCCESS));
        assertCall().accept(actual, HttpStatus.INTERNAL_SERVER_ERROR);
        var actualAccessToken = actual.getHeaders().get(HEADER_ACCESS_TOKEN_NAME);
        assertNull(actualAccessToken);
        verify(auth2ServiceMock).saveOAuthInfo(any());
    }
}