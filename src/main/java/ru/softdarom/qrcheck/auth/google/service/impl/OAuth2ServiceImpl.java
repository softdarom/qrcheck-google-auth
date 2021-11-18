package ru.softdarom.qrcheck.auth.google.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.auth.google.exception.NotAuthenticatedException;
import ru.softdarom.qrcheck.auth.google.model.dto.response.AuthHandlerUserResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleAccessTokenResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleTokenInfoResponse;
import ru.softdarom.qrcheck.auth.google.service.AuthHandlerService;
import ru.softdarom.qrcheck.auth.google.service.GoogleService;
import ru.softdarom.qrcheck.auth.google.service.OAuth2Service;

import java.util.Objects;

@Service
@Slf4j(topic = "SERVICE")
public class OAuth2ServiceImpl implements OAuth2Service {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final AuthHandlerService authHandlerService;
    private final GoogleService googleService;

    @Autowired
    OAuth2ServiceImpl(OAuth2AuthorizedClientService authorizedClientService,
                      AuthHandlerService authHandlerService,
                      GoogleService googleService) {
        this.authorizedClientService = authorizedClientService;
        this.authHandlerService = authHandlerService;
        this.googleService = googleService;
    }

    @Override
    public OAuth2AuthorizedClient getOAuthClient(Authentication authentication) {
        checkNullAuthentication(authentication);
        LOGGER.info("Getting an oAuth2 client by '{}' authentication.", authentication.getName());
        var oAuthToken = (OAuth2AuthenticationToken) authentication;
        return authorizedClientService.loadAuthorizedClient(
                oAuthToken.getAuthorizedClientRegistrationId(),
                oAuthToken.getName()
        );
    }

    @Override
    public AuthHandlerUserResponse saveOAuthInfo(Authentication authentication) {
        checkNullAuthentication(authentication);
        return authHandlerService.saveOAuthInfo((OAuth2User) authentication.getPrincipal(), getOAuthClient(authentication));
    }

    @Override
    public GoogleTokenInfoResponse tokenInfo(String accessToken) {
        Assert.hasText(accessToken, "The 'accessToken' must not be null or empty!");
        return googleService.tokenInfo(accessToken);
    }

    @Override
    public GoogleAccessTokenResponse refresh(String refreshToken) {
        Assert.hasText(refreshToken, "The 'refreshToken' must not be null or empty!");
        return googleService.refresh(refreshToken);
    }

    private void checkNullAuthentication(Authentication authentication) {
        if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
            return;
        }
        throw new NotAuthenticatedException("A user is not authenticated!");
    }
}