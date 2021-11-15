package ru.softdarom.qrcheck.auth.google.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.auth.google.client.GoogleClient;
import ru.softdarom.qrcheck.auth.google.model.dto.request.GoogleRefreshTokenRequest;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleAccessTokenResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleTokenInfoResponse;
import ru.softdarom.qrcheck.auth.google.service.GoogleService;

@Service
@Slf4j(topic = "SERVICE")
public class GoogleServiceImpl implements GoogleService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    private final GoogleClient googleClient;

    @Autowired
    GoogleServiceImpl(GoogleClient googleClient) {
        this.googleClient = googleClient;
    }

    @Override
    public GoogleTokenInfoResponse tokenInfo(String accessToken) {
        Assert.hasText(accessToken, "The 'accessToken' must not be null or empty!");
        return googleClient.tokenInfo(accessToken).getBody();
    }

    @Override
    public GoogleAccessTokenResponse refresh(String refreshToken) {
        Assert.hasText(refreshToken, "The 'refreshToken' must not be null or empty!");
        return googleClient.refresh(new GoogleRefreshTokenRequest(clientId, clientSecret, refreshToken)).getBody();
    }
}