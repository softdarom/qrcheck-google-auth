package ru.softdarom.qrcheck.auth.google.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.softdarom.qrcheck.auth.google.model.dto.UserDto;
import ru.softdarom.qrcheck.auth.google.model.dto.response.BaseResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleAccessTokenResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleTokenInfoResponse;
import ru.softdarom.qrcheck.auth.google.service.OAuth2Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    private static final String HEADER_ACCESS_TOKEN_NAME = "X-Authorization-Token";

    private final OAuth2Service auth2Service;

    @Autowired
    OAuth2Controller(OAuth2Service auth2Service) {
        this.auth2Service = auth2Service;
    }

    @GetMapping("/success")
    public ResponseEntity<UserDto> success(Authentication authentication, HttpServletResponse response) throws IOException {
        try {
            var oAuth2Info = auth2Service.saveOAuthInfo(authentication);
            return ResponseEntity.ok()
                    .header(HEADER_ACCESS_TOKEN_NAME, oAuth2Info.getToken().getAccessToken().getToken())
                    .body(oAuth2Info.getUser());
        } catch (Exception e) {
            response.sendRedirect("/oauth2/failure?message=" + e.getMessage());
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }
    }

    @GetMapping("/failure")
    public ResponseEntity<BaseResponse> failure(@RequestParam(required = false) String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(Optional.ofNullable(message).orElse("Unknown error")));
    }

    @GetMapping("/tokens/info")
    public ResponseEntity<GoogleTokenInfoResponse> getTokenInfo(@RequestParam String accessToken) {
        return ResponseEntity.ok(auth2Service.tokenInfo(accessToken));
    }

    @PostMapping("/tokens/refresh")
    public ResponseEntity<GoogleAccessTokenResponse> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(auth2Service.refresh(refreshToken));
    }
}