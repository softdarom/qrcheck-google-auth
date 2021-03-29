package ru.softdarom.qrcheck.auth.google.service;

import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;

public interface UserSaverService {

    void saveOrUpdate(GoogleUserDto userDto, GoogleTokenDto tokenDto);

}