package ru.softdarom.qrcheck.auth.google.test.generator;

import org.mockito.Mockito;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;

import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateInteger;
import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.generateString;
import static ru.softdarom.qrcheck.auth.google.test.helper.UriHelper.generateUri;

public final class OAuthGenerator {

    public static OAuth2AccessToken oAuth2AccessToken() {
        return new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                generateString(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC),
                LocalDateTime.now().toInstant(ZoneOffset.UTC),
                Set.of(generateString())
        );
    }

    public static OAuth2UserRequest oAuth2UserRequest() {
        return new OAuth2UserRequest(
                ClientRegistration
                        .withRegistrationId("google")
                        .userInfoUri(generateUri(generateString(), generateInteger(80, 8000)))
                        .userNameAttributeName("google")
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .clientId(generateString())
                        .tokenUri(generateString())
                        .build(),
                oAuth2AccessToken(),
                Map.of("id_token", generateString())
        );
    }

    public static OAuth2User oAuth2User() {
        return new DefaultOAuth2User(
                Set.of(
                        new SimpleGrantedAuthority("ROLE_USER")
                ),
                Map.of(
                        "sub", generateString(),
                        "given_name", generateString(),
                        "family_name", generateString(),
                        "picture", generateString(),
                        "email", generateString()
                ),
                "sub"
        );
    }
}