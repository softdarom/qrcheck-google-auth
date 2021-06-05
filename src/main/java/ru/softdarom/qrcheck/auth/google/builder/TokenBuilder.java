package ru.softdarom.qrcheck.auth.google.builder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.auth.google.model.dto.TokenDto;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Slf4j(topic = "GOOGLE-AUTH-BUILDER")
public final class TokenBuilder {

    private final OAuth2AuthorizedClient oAuth2AuthorizedClient;

    public TokenBuilder(OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        checkArgs(oAuth2AuthorizedClient);
        this.oAuth2AuthorizedClient = oAuth2AuthorizedClient;
    }

    public TokenDto build() {
        LOGGER.debug("Building a TokenDto by {}", JsonHelper.asJson(oAuth2AuthorizedClient));
        var sub = oAuth2AuthorizedClient.getPrincipalName();
        var accessToken = new TokenDto.AccessToken(oAuth2AuthorizedClient.getAccessToken());
        var refreshToken = new TokenDto.RefreshToken(oAuth2AuthorizedClient.getRefreshToken());
        return new TokenDto(sub, accessToken, refreshToken);
    }

    private void checkArgs(OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        Assert.notNull(oAuth2AuthorizedClient, "The 'oAuth2AuthorizedClient' must not be null!");
        Assert.notNull(oAuth2AuthorizedClient.getAccessToken(), "The 'accessToken' must not be null!");
        Assert.notNull(oAuth2AuthorizedClient.getRefreshToken(), "The 'refreshToken' must not be null!");
    }
}