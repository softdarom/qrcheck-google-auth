package ru.softdarom.qrcheck.auth.google.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.softdarom.qrcheck.auth.google.test.tag.UnitTest;

import static org.junit.jupiter.api.Assertions.*;
import static ru.softdarom.qrcheck.auth.google.test.generator.OAuthGenerator.oAuth2AuthorizedClient;

@UnitTest
@DisplayName("TokenBuilder Unit Test")
class TokenBuilderTest {

    //  -----------------------   successful tests   -------------------------

    @Test
    @DisplayName("build(): returns correct dto'")
    void successfulBuild() {
        var actual = new TokenBuilder(oAuth2AuthorizedClient()).build();
        assertAll(() -> {
            assertNotNull(actual);

            assertNotNull(actual.getSub());
            assertNotNull(actual.getProvider());
            assertNotNull(actual.getAccessToken());
            assertNotNull(actual.getRefreshToken());

            assertFalse(actual.getAccessToken().getToken().isEmpty());
            assertNotNull(actual.getAccessToken().getIssuedAt());
            assertNotNull(actual.getAccessToken().getExpiresAt());

            assertFalse(actual.getRefreshToken().getToken().isEmpty());
        });
    }

    //  -----------------------   failure tests   -------------------------

    @Test
    @DisplayName("build(): throws IllegalArgumentException when oAuth2AuthorizedClient is null")
    void failureBuildOAuth2AuthorizedClientNull() {
        assertThrows(IllegalArgumentException.class, () -> new TokenBuilder(null).build());
    }

    @Test
    @DisplayName("build(): throws IllegalArgumentException when access token is null")
    void failureBuildAccessTokenNull() {
        var actual = new TokenBuilder(oAuth2AuthorizedClient()).build();
        actual.setAccessToken(null);
        assertThrows(IllegalArgumentException.class, () -> new TokenBuilder(null).build());
    }

    @Test
    @DisplayName("build(): throws IllegalArgumentException when refresh token is null")
    void failureBuildRefreshTokenNull() {
        var actual = new TokenBuilder(oAuth2AuthorizedClient()).build();
        actual.setRefreshToken(null);
        assertThrows(IllegalArgumentException.class, () -> new TokenBuilder(null).build());
    }
}