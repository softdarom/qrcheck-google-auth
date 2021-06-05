package ru.softdarom.qrcheck.auth.google.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class GoogleTokenInfoResponse {

    @JsonProperty("azp")
    private String azp;

    @JsonProperty("aud")
    private String aud;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("email")
    private String email;

}