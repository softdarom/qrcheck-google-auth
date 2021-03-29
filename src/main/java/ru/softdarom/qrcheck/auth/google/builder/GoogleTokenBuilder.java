package ru.softdarom.qrcheck.auth.google.builder;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;

public final class GoogleTokenBuilder {

    private final OAuth2UserRequest oAuth2UserRequest;

    public GoogleTokenBuilder(OAuth2UserRequest oAuth2UserRequest) {
        this.oAuth2UserRequest = oAuth2UserRequest;
    }

    public GoogleTokenDto build() {
        var accessToken = new GoogleTokenDto.AccessToken(oAuth2UserRequest.getAccessToken());
        var refreshToken = new GoogleTokenDto.RefreshToken((String) oAuth2UserRequest.getAdditionalParameters().get("id_token"));
        return new GoogleTokenDto(accessToken, refreshToken);
    }
}