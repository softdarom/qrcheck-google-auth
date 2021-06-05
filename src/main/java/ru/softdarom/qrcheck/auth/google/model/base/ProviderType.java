package ru.softdarom.qrcheck.auth.google.model.base;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProviderType {

    GOOGLE("google");

    @JsonValue
    private final String type;

    @Override
    public String toString() {
        return String.valueOf(type);
    }
}