package ru.softdarom.qrcheck.auth.google.test.generator;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.softdarom.qrcheck.auth.google.builder.GoogleTokenBuilder;
import ru.softdarom.qrcheck.auth.google.builder.GoogleUserBuilder;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2DeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2UpdateDeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.response.GoogleUserResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;

import static ru.softdarom.qrcheck.auth.google.test.generator.CommonGenerator.*;
import static ru.softdarom.qrcheck.auth.google.test.helper.UriHelper.generateUri;

public final class OAuthGenerator {

    public static OAuth2UpdateDeviceRequest oAuth2UpdateDeviceRequest() {
        var request = new OAuth2UpdateDeviceRequest();
        request.setUserId(generateString());
        request.setOldDeviceDto(deviceInfoDto());
        request.setNewDeviceDto(deviceInfoDto());
        return request;
    }

    public static OAuth2UpdateDeviceRequest.DeviceInfoDto deviceInfoDto() {
        var dto = new OAuth2UpdateDeviceRequest.DeviceInfoDto();
        dto.setDeviceId(generateString());
        dto.setDevicePushToken(generateString());
        return dto;
    }

    public static GoogleUserResponse googleUserResponse() {
        var response = new GoogleUserResponse();
        response.setId(generateLong());
        return response;
    }

    public static OAuth2DeviceRequest oAuth2DeviceRequest() {
        var request = new OAuth2DeviceRequest();
        request.setUserId(generateString());
        request.setDeviceId(generateString());
        request.setDevicePushToken(generateString());
        return request;
    }

    public static GoogleCredentialRequest googleCredentialRequest() {
        return new GoogleCredentialRequest(googleTokenDto(), googleUserDto());
    }

    public static GoogleUserDto googleUserDto() {
        return new GoogleUserBuilder(oAuth2User()).build();
    }

    public static GoogleTokenDto googleTokenDto() {
        return new GoogleTokenBuilder(oAuth2UserRequest()).build();
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