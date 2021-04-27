package ru.softdarom.qrcheck.auth.google.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleUserResponse {

    @JsonProperty("id")
    private Long id;

}