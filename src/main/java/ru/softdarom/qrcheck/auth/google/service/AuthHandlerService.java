package ru.softdarom.qrcheck.auth.google.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.softdarom.qrcheck.auth.google.model.dto.response.AuthHandlerUserResponse;

public interface AuthHandlerService {

    AuthHandlerUserResponse saveOAuthInfo(OAuth2User oAuth2User, OAuth2AuthorizedClient oAuth2AuthorizedClient);

}