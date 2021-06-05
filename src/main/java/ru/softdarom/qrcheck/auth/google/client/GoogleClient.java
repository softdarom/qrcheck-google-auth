package ru.softdarom.qrcheck.auth.google.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.softdarom.qrcheck.auth.google.model.dto.request.GoogleRefreshTokenRequest;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleAccessTokenResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleTokenInfoResponse;

@FeignClient(name = "google", url = "${spring.security.oauth2.client.registration.google.api.url}")
public interface GoogleClient {

    @GetMapping("${spring.security.oauth2.client.registration.google.api.token-info}")
    ResponseEntity<GoogleTokenInfoResponse> tokenInfo(@RequestParam(name = "access_token") String accessToken);

    @PostMapping(value = "${spring.security.oauth2.client.registration.google.api.token-refresh}")
    ResponseEntity<GoogleAccessTokenResponse> refresh(GoogleRefreshTokenRequest request);

}