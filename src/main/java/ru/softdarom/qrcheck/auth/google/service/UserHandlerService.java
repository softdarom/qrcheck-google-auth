package ru.softdarom.qrcheck.auth.google.service;

import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;

public interface UserHandlerService {

    void saveOrUpdate(GoogleUserDto userDto, GoogleTokenDto tokenDto);

    void exist(String email);
}