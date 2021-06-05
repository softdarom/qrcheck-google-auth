package ru.softdarom.qrcheck.auth.google.model.base;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Generated
@RequiredArgsConstructor
public enum GrantType {

    REFRESH("refresh_token");

    @JsonValue
    private final String grant;

    @Override
    public String toString() {
        return String.valueOf(grant);
    }
}