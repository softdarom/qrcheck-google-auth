package ru.softdarom.qrcheck.auth.google.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.softdarom.qrcheck.auth.google.test.tag.UnitTest;

import static org.junit.jupiter.api.Assertions.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2User;

@UnitTest
@DisplayName("GoogleUserBuilder Unit Test")
class GoogleUserBuilderTest {

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("build(): returns correct dto'")
    void successfulBuild() {
        var actual = new GoogleUserBuilder(oAuth2User()).build();
        assertNotNull(actual);

        assertFalse(actual.getFirstName().isEmpty());
        assertFalse(actual.getSecondName().isEmpty());
        assertFalse(actual.getAvatar().isEmpty());
        assertFalse(actual.getEmail().isEmpty());
    }

}