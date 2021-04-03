package ru.softdarom.qrcheck.auth.google.model.dto;

import lombok.Data;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Data
public class GoogleTokenDto {

    private final AccessToken accessToken;
    private final RefreshToken refreshToken;

    public GoogleTokenDto(AccessToken accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Data
    public static class AccessToken {
        private final String token;
        private final LocalDateTime issuedAt;
        private final LocalDateTime expiresAt;
        private final Set<String> scopes;

        public AccessToken(OAuth2AccessToken auth2AccessToken) {
            this.token = auth2AccessToken.getTokenValue();
            this.scopes = auth2AccessToken.getScopes();
            this.issuedAt = extract(auth2AccessToken.getIssuedAt());
            this.expiresAt = extract(auth2AccessToken.getExpiresAt());
        }

        private LocalDateTime extract(Instant instant) {
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        }
    }

    @Data
    public static class RefreshToken {
        private final String token;
    }
}