package ru.softdarom.qrcheck.auth.google.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class GoogleAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

}