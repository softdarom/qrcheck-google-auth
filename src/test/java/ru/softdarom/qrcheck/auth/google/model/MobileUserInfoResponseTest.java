package ru.softdarom.qrcheck.auth.google.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.softdarom.qrcheck.auth.google.model.dto.response.MobileUserInfoResponse;
import ru.softdarom.qrcheck.auth.google.test.tag.UnitTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.*;

@UnitTest
@DisplayName("MobileUserInfoResponse Unit Test")
class MobileUserInfoResponseTest {

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("new(): returns correct response")
    void successfulCreateNewObject() {
        var actual = new MobileUserInfoResponse(authHandlerUserResponse());
        assertAll(() -> {
            assertNotNull(actual);

            assertNotNull(actual.getId());
            assertNotNull(actual.getFirstName());
            assertNotNull(actual.getSecondName());
            assertNotNull(actual.getEmail());
            assertNotNull(actual.getAccessToken());
        });
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("new(): throws NoSuchElementException when AuthHandlerUserResponse is null")
    void successfulCreateNewObjectNullAuthHandlerUserResponse() {
        assertThrows(NoSuchElementException.class, () -> new MobileUserInfoResponse(null));
    }

    @Test
    @DisplayName("new(): throws NoSuchElementException when UserDto is null")
    void successfulCreateNewObjectNullUserDto() {
        assertThrows(NoSuchElementException.class, () -> new MobileUserInfoResponse(authHandlerUserResponseNullUserDto()));
    }

    @Test
    @DisplayName("new(): throws NoSuchElementException when TokenDto is null")
    void successfulCreateNewObjectNullTokenDto() {
        assertThrows(NoSuchElementException.class, () -> new MobileUserInfoResponse(authHandlerUserResponseNullTokenDto()));
    }
}