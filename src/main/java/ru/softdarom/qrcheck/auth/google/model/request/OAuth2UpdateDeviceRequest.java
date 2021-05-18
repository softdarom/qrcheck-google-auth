package ru.softdarom.qrcheck.auth.google.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Generated
@Data
public class OAuth2UpdateDeviceRequest {

    @NotEmpty
    @JsonProperty("userId")
    private String userId;

    @NotNull
    @JsonProperty("oldDevice")
    private DeviceInfoDto oldDeviceDto;

    @NotNull
    @JsonProperty("newDevice")
    private DeviceInfoDto newDeviceDto;

    @Data
    public static class DeviceInfoDto {

        @NotEmpty
        @JsonProperty("deviceId")
        private String deviceId;

        @NotEmpty
        @JsonProperty("devicePushToken")
        private String devicePushToken;

    }

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}