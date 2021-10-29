package ru.softdarom.qrcheck.auth.google.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

import java.util.Optional;

@Generated
@Data
@NoArgsConstructor
public class MobileUserInfoResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("secondName")
    private String secondName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("accessToken")
    private String accessToken;

    public MobileUserInfoResponse(AuthHandlerUserResponse response) {
        var notNullResponse = Optional.ofNullable(response).orElseThrow();
        setUserInfo(notNullResponse);
        setAccessToken(notNullResponse);
    }

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }

    @JsonIgnore
    private void setUserInfo(AuthHandlerUserResponse notNullResponse) {
        var notNullUser = Optional.ofNullable(notNullResponse.getUser()).orElseThrow();
        this.id = notNullUser.getId();
        this.firstName = notNullUser.getFirstName();
        this.secondName = notNullUser.getSecondName();
        this.email = notNullUser.getEmail();
    }

    @JsonIgnore
    private void setAccessToken(AuthHandlerUserResponse notNullResponse) {
        var notNullToken = Optional.ofNullable(notNullResponse.getToken()).orElseThrow();
        this.accessToken = notNullToken.getAccessToken().getToken();
    }
}