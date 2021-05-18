package ru.softdarom.qrcheck.auth.google.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Generated
@Data
public class GoogleUserResponse {

    @JsonProperty("id")
    private Long id;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}