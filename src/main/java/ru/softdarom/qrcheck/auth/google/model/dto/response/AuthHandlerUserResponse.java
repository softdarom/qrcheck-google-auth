package ru.softdarom.qrcheck.auth.google.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.auth.google.model.dto.TokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.UserDto;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Generated
@Data
public class AuthHandlerUserResponse {

    @JsonProperty("user")
    private final UserDto user;

    @JsonProperty("token")
    private final TokenDto token;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}