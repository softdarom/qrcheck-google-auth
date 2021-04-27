package ru.softdarom.qrcheck.auth.google.service;

import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2DeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2UpdateDeviceRequest;

public interface UserHandlerService {

    void saveUser(GoogleUserDto userDto, GoogleTokenDto tokenDto);

    void saveUserDevice(OAuth2DeviceRequest request);

    void updateUserDevice(OAuth2UpdateDeviceRequest request);

    Long exist(String email);
}