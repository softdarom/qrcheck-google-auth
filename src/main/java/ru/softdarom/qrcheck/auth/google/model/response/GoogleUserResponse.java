package ru.softdarom.qrcheck.auth.google.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Data
public class GoogleUserResponse {

    @JsonProperty("id")
    private Long id;

    @Override
    public String toString() {
        return JsonHelper.asJson(this);
    }
}