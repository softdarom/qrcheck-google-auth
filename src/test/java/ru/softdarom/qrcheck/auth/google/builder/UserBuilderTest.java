package ru.softdarom.qrcheck.auth.google.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.softdarom.qrcheck.auth.google.test.tag.UnitTest;

import static org.junit.jupiter.api.Assertions.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2User;

@UnitTest
@DisplayName("UserBuilder Unit Test")
class UserBuilderTest {

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("build(): returns correct dto'")
    void successfulBuild() {
        var actual = new UserBuilder(oAuth2User()).build();
        assertAll(() -> {
            assertNotNull(actual);

            assertFalse(actual.getFirstName().isEmpty());
            assertFalse(actual.getSecondName().isEmpty());
            assertFalse(actual.getEmail().isEmpty());
            assertFalse(actual.getPicture().isEmpty());
        });
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("build(): throws IllegalArgumentException when oAuth2User is null")
    void failureBuildUserIdNull() {
        assertThrows(IllegalArgumentException.class, () -> new UserBuilder(null).build());
    }
}