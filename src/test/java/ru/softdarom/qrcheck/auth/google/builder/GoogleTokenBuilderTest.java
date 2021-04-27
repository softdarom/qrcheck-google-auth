package ru.softdarom.qrcheck.auth.google.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.softdarom.qrcheck.auth.google.test.tag.UnitTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2UserRequest;

@UnitTest
@DisplayName("GoogleTokenBuilder Unit Test")
class GoogleTokenBuilderTest {

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("build(): returns correct dto'")
    void successfulBuild() {
        var actual = new GoogleTokenBuilder(oAuth2UserRequest()).build();
        assertNotNull(actual);

        assertNotNull(actual.getAccessTokenDto());
        assertNotNull(actual.getRefreshTokenDto());

        assertFalse(actual.getAccessTokenDto().getToken().isEmpty());
        assertFalse(actual.getAccessTokenDto().getScopes().isEmpty());
        assertNotNull(actual.getAccessTokenDto().getIssuedAt());
        assertNotNull(actual.getAccessTokenDto().getExpiresAt());

        assertFalse(actual.getRefreshTokenDto().getToken().isEmpty());
    }

}