package ru.softdarom.qrcheck.auth.google.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

import javax.validation.constraints.NotNull;

@Generated
@Data
public class GoogleCredentialRequest {

    @NotNull
    @JsonProperty("token")
    private final GoogleTokenDto tokenDto;

    @NotNull
    @JsonProperty("user")
    private final GoogleUserDto userDto;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}