package ru.softdarom.qrcheck.auth.google.service;

import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleAccessTokenResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleTokenInfoResponse;

public interface GoogleService {

    GoogleTokenInfoResponse tokenInfo(String accessToken);

    GoogleAccessTokenResponse refresh(String refreshToken);

}