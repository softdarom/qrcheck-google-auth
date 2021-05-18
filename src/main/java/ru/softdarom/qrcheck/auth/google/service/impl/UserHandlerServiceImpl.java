package ru.softdarom.qrcheck.auth.google.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2DeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2UpdateDeviceRequest;
import ru.softdarom.qrcheck.auth.google.service.UserHandlerExternalService;
import ru.softdarom.qrcheck.auth.google.service.UserHandlerService;

@Slf4j(topic = "GOOGLE-AUTH-SERVICE")
@Service
public class UserHandlerServiceImpl implements UserHandlerService {

    private final UserHandlerExternalService userHandlerExternalService;

    @Autowired
    UserHandlerServiceImpl(UserHandlerExternalService userHandlerExternalService) {
        this.userHandlerExternalService = userHandlerExternalService;
    }

    @Override
    public void saveUser(GoogleUserDto userDto, GoogleTokenDto tokenDto) {
        Assert.notNull(userDto, "The 'userDto' must not null!");
        Assert.notNull(tokenDto, "The 'tokenDto' must not null!");
        LOGGER.info("A user (id: {}) will be saved.", userDto.getEmail());
        userHandlerExternalService.saveUser(new GoogleCredentialRequest(tokenDto, userDto));
    }

    @Override
    public void saveUserDevice(OAuth2DeviceRequest request) {
        Assert.notNull(request, "The 'request' must not null!");
        LOGGER.info("A user's (id: {}) device (id: {}) will be saved.", request.getUserId(), request.getDeviceId());
        userHandlerExternalService.saveDevice(request);
    }

    @Override
    public void updateUserDevice(OAuth2UpdateDeviceRequest request) {
        Assert.notNull(request, "The 'request' must not null!");
        LOGGER.info("A user's (id: {}) device (old id: {}, new id: {}) will be saved.",
                request.getUserId(), request.getOldDeviceDto().getDeviceId(), request.getNewDeviceDto().getDeviceId());
        userHandlerExternalService.updateDevice(request);
    }

    @Override
    public Long exist(String email) {
        Assert.notNull(email, "The 'email' must not be null!");
        Assert.isTrue(!email.isEmpty(), "The 'email' must not be empty!");
        LOGGER.info("Check of existing of a user by id: {}", email);
        return userHandlerExternalService.getUser(email).getId();
    }
}