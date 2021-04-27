package ru.softdarom.qrcheck.auth.google.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;

import javax.validation.constraints.NotNull;

@Data
public class GoogleCredentialRequest {

    @NotNull
    @JsonProperty("token")
    private final GoogleTokenDto tokenDto;

    @NotNull
    @JsonProperty("user")
    private final GoogleUserDto userDto;

}