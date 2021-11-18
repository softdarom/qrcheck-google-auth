package ru.softdarom.qrcheck.auth.google.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.auth.google.builder.TokenBuilder;
import ru.softdarom.qrcheck.auth.google.builder.UserBuilder;
import ru.softdarom.qrcheck.auth.google.client.AuthHandlerClient;
import ru.softdarom.qrcheck.auth.google.config.property.ApiKeyProperties;
import ru.softdarom.qrcheck.auth.google.model.dto.TokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.UserDto;
import ru.softdarom.qrcheck.auth.google.model.dto.request.AuthHandlerTokenUserInfoRequest;
import ru.softdarom.qrcheck.auth.google.model.dto.response.AuthHandlerUserResponse;
import ru.softdarom.qrcheck.auth.google.service.AuthHandlerService;

@Service
@Slf4j(topic = "SERVICE")
public class AuthHandlerServiceImpl implements AuthHandlerService {

    private final AuthHandlerClient authHandlerClient;
    private final ApiKeyProperties properties;

    @Autowired
    AuthHandlerServiceImpl(AuthHandlerClient authHandlerClient, ApiKeyProperties properties) {
        this.authHandlerClient = authHandlerClient;
        this.properties = properties;
    }

    @Override
    public AuthHandlerUserResponse saveOAuthInfo(OAuth2User oAuth2User, OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        Assert.notNull(oAuth2User, "The 'oAuth2User' must not null!");
        Assert.notNull(oAuth2AuthorizedClient, "The 'oAuth2AuthorizedClient' must not null!");
        var user = buildGoogleOAuth2User(oAuth2User);
        var token = buildGoogleToken(oAuth2AuthorizedClient);
        var request = new AuthHandlerTokenUserInfoRequest(user, token);
        LOGGER.info("A oAuth2Info (value: {}) will be saved.", request);
        var response = authHandlerClient.saveOAuth2Info(properties.getToken().getOutgoing(), request);
        return response.getBody();
    }

    private UserDto buildGoogleOAuth2User(OAuth2User oAuth2User) {
        return new UserBuilder(oAuth2User).build();
    }

    private TokenDto buildGoogleToken(OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        return new TokenBuilder(oAuth2AuthorizedClient).build();
    }
}