package ru.softdarom.qrcheck.auth.google.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Generated
@Data
@NoArgsConstructor
public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("secondName")
    private String secondName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("picture")
    private String picture;

    public UserDto(String firstName, String secondName, String email, String picture) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}