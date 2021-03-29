package ru.softdarom.qrcheck.auth.google.model.request;

import lombok.Data;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;

@Data
public class GoogleCredentialRequest {

    private final GoogleTokenDto tokenDto;
    private final GoogleUserDto userDto;

}