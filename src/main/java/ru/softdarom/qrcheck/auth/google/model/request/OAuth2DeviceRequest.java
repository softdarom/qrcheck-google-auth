package ru.softdarom.qrcheck.auth.google.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OAuth2DeviceRequest {

    @NotEmpty
    @JsonProperty("userId")
    private String userId;

    @NotEmpty
    @JsonProperty("deviceId")
    private String deviceId;

    @NotEmpty
    @JsonProperty("devicePushToken")
    private String devicePushToken;
}