package ru.softdarom.qrcheck.auth.google.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Data
public class GoogleTokenDto {

    @NotNull
    @JsonProperty("accessToken")
    private final AccessToken accessTokenDto;

    @NotNull
    @JsonProperty("refreshToken")
    private final RefreshToken refreshTokenDto;

    public GoogleTokenDto(AccessToken accessTokenDto, RefreshToken refreshTokenDto) {
        this.accessTokenDto = accessTokenDto;
        this.refreshTokenDto = refreshTokenDto;
    }

    @Data
    public static class AccessToken {

        @NotEmpty
        @JsonProperty("token")
        private final String token;

        @NotNull
        @JsonProperty("issuedAt")
        private final LocalDateTime issuedAt;

        @NotNull
        @JsonProperty("expiresAt")
        private final LocalDateTime expiresAt;

        @NotEmpty
        @JsonProperty("scopes")
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

        @NotEmpty
        @JsonProperty("token")
        private final String token;
    }
}