package ru.softdarom.qrcheck.auth.google.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import ru.softdarom.qrcheck.auth.google.model.dto.response.AuthHandlerUserResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleAccessTokenResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleTokenInfoResponse;

public interface OAuth2Service {

    OAuth2AuthorizedClient getOAuthClient(Authentication authentication);

    AuthHandlerUserResponse saveOAuthInfo(Authentication authentication);

    GoogleTokenInfoResponse tokenInfo(String accessToken);

    GoogleAccessTokenResponse refresh(String refreshToken);
}