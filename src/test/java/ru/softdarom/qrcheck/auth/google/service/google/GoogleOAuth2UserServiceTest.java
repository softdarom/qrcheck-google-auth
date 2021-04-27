package ru.softdarom.qrcheck.auth.google.service.google;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestOperations;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.service.UserHandlerService;
import ru.softdarom.qrcheck.auth.google.test.tag.SpringIntegrationTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateString;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2UserRequest;

@SpringIntegrationTest
@DisplayName("GoogleOAuth2UserService Spring Integration Test")
class GoogleOAuth2UserServiceTest {

    @Mock
    private UserHandlerService userHandlerServiceMock;

    @Mock
    private RestOperations restOperationsMock;

    @Autowired
    private GoogleOAuth2UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "userHandlerService", userHandlerServiceMock);
        ReflectionTestUtils.setField(service, "restOperations", restOperationsMock);
    }

    @AfterEach
    void tearDown() {
        reset(userHandlerServiceMock, restOperationsMock);
    }

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("loadUser(): returns OAuth2User")
    void successfulLoadUser() {
        doNothing().when(userHandlerServiceMock).saveUser(any(GoogleUserDto.class), any(GoogleTokenDto.class));
        when(restOperationsMock.exchange(any(RequestEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(Map.of("google", generateString()), HttpStatus.OK));
        assertDoesNotThrow(() -> service.loadUser(oAuth2UserRequest()));
        verify(userHandlerServiceMock).saveUser(any(GoogleUserDto.class), any(GoogleTokenDto.class));
    }

    //  -----------------------   fail tests   -------------------------
}