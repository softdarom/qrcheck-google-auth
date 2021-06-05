package ru.softdarom.qrcheck.auth.google.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.auth.google.model.base.GrantType;

@Data
@Generated
public class GoogleRefreshTokenRequest {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("grant_type")
    private GrantType grantType = GrantType.REFRESH;

    public GoogleRefreshTokenRequest(String clientId, String clientSecret, String refreshToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
    }
}