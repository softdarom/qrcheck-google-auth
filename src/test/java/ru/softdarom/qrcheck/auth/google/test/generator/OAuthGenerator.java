package ru.softdarom.qrcheck.auth.google.test.generator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.softdarom.qrcheck.auth.google.builder.TokenBuilder;
import ru.softdarom.qrcheck.auth.google.builder.UserBuilder;
import ru.softdarom.qrcheck.auth.google.model.dto.TokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.UserDto;
import ru.softdarom.qrcheck.auth.google.model.dto.request.AuthHandlerTokenUserInfoRequest;
import ru.softdarom.qrcheck.auth.google.model.dto.request.GoogleRefreshTokenRequest;
import ru.softdarom.qrcheck.auth.google.model.dto.response.AuthHandlerUserResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleAccessTokenResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleTokenInfoResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;

import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.*;
import static ru.softdarom.qrcheck.auth.google.test.helper.UriHelper.generateUri;

public final class OAuthGenerator {

    public static UserDto userDto() {
        var dto = new UserBuilder(oAuth2User()).build();
        dto.setId(generateLong());
        return dto;
    }

    public static TokenDto tokenDto() {
        return new TokenBuilder(oAuth2AuthorizedClient()).build();
    }

    public static AuthHandlerTokenUserInfoRequest authHandlerTokenUserInfoRequest() {
        return new AuthHandlerTokenUserInfoRequest(userDto(), tokenDto());
    }

    public static GoogleRefreshTokenRequest googleRefreshTokenRequest() {
        return new GoogleRefreshTokenRequest(generateString(), generateString(), generateString());
    }

    public static AuthHandlerUserResponse authHandlerUserResponse() {
        return new AuthHandlerUserResponse(userDto(), tokenDto());
    }

    public static GoogleTokenInfoResponse googleTokenInfoResponse() {
        var response = new GoogleTokenInfoResponse();
        response.setAud(generateString());
        response.setAud(generateString());
        response.setSub(generateString());
        response.setScope(generateString());
        response.setEmail(generateString());
        return response;
    }

    public static GoogleAccessTokenResponse googleAccessTokenResponse() {
        var response = new GoogleAccessTokenResponse();
        response.setAccessToken(generateString());
        return response;
    }

    public static OAuth2AuthorizedClient oAuth2AuthorizedClient() {
        return new OAuth2AuthorizedClient(
                clientRegistration(), generateString(),
                oAuth2AccessToken(), oAuth2RefreshToken()
        );
    }

    public static ClientRegistration clientRegistration() {
        return ClientRegistration
                .withRegistrationId("google")
                .userInfoUri(generateUri(generateString(), generateInteger(80, 8000)))
                .userNameAttributeName("google")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientId(generateString())
                .tokenUri(generateString())
                .build();
    }

    public static OAuth2AccessToken oAuth2AccessToken() {
        return new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                generateString(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC),
                LocalDateTime.now().toInstant(ZoneOffset.UTC),
                Set.of(generateString())
        );
    }

    public static OAuth2RefreshToken oAuth2RefreshToken() {
        return new OAuth2RefreshToken(
                generateString(), LocalDateTime.now().toInstant(ZoneOffset.UTC)
        );
    }

    public static OAuth2User oAuth2User() {
        return new DefaultOAuth2User(
                authorities(),
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

    public static Authentication authentication() {
        return new OAuth2AuthenticationToken(oAuth2User(), authorities(), generateString());
    }

    public static Set<? extends GrantedAuthority> authorities() {
        return Set.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("SCOPE_https://www.googleapis.com/auth/userinfo.email"),
                new SimpleGrantedAuthority("SCOPE_https://www.googleapis.com/auth/userinfo.profile")
        );
    }
}