package ru.softdarom.qrcheck.auth.google.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.auth.google.model.dto.TokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.UserDto;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Generated
@Data
public class AuthHandlerTokenUserInfoRequest {

    @JsonProperty("user")
    private final UserDto user;

    @JsonProperty("token")
    private final TokenDto token;

    public AuthHandlerTokenUserInfoRequest(UserDto user, TokenDto token) {
        this.user = user;
        this.token = token;
    }

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }

}