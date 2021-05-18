package ru.softdarom.qrcheck.auth.google.builder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Slf4j(topic = "GOOGLE-AUTH-BUILDER")
public final class GoogleTokenBuilder {

    private final OAuth2UserRequest oAuth2UserRequest;

    public GoogleTokenBuilder(OAuth2UserRequest oAuth2UserRequest) {
        this.oAuth2UserRequest = oAuth2UserRequest;
    }

    public GoogleTokenDto build() {
        LOGGER.debug("Building a GoogleTokenDto by {}", JsonHelper.asJson(oAuth2UserRequest));
        var accessToken = new GoogleTokenDto.AccessToken(oAuth2UserRequest.getAccessToken());
        var refreshToken = new GoogleTokenDto.RefreshToken((String) oAuth2UserRequest.getAdditionalParameters().get("id_token"));
        return new GoogleTokenDto(accessToken, refreshToken);
    }
}