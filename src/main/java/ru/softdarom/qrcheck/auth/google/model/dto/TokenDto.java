package ru.softdarom.qrcheck.auth.google.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.auth.google.model.base.ProviderType;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Generated
@Data
@NoArgsConstructor
public class TokenDto {

    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Europe/Moscow");

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("provider")
    private ProviderType provider = ProviderType.GOOGLE;

    @JsonProperty("accessToken")
    private AccessToken accessToken;

    @JsonProperty("refreshToken")
    private RefreshToken refreshToken;

    public TokenDto(String sub, AccessToken accessToken, RefreshToken refreshToken) {
        this.sub = sub;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AccessToken {

        @JsonProperty("token")
        private String token;

        @JsonProperty("issuedAt")
        private LocalDateTime issuedAt;

        @JsonProperty("expiresAt")
        private LocalDateTime expiresAt;

        public AccessToken(OAuth2AccessToken auth2AccessToken) {
            Assert.notNull(auth2AccessToken, "The 'auth2AccessToken' must not be null!");
            this.token = auth2AccessToken.getTokenValue();
            this.issuedAt = extract(auth2AccessToken.getIssuedAt());
            this.expiresAt = extract(auth2AccessToken.getExpiresAt());
        }
    }

    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RefreshToken {

        @JsonProperty("token")
        private String token;

        @JsonProperty("issuedAt")
        private LocalDateTime issuedAt;

        public RefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
            Assert.notNull(oAuth2RefreshToken, "The 'oAuth2RefreshToken' must not be null!");
            this.token = oAuth2RefreshToken.getTokenValue();
            this.issuedAt = extract(oAuth2RefreshToken.getIssuedAt());
        }
    }

    private static LocalDateTime extract(Instant instant) {
        return LocalDateTime.ofInstant(instant, DEFAULT_ZONE);
    }

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}