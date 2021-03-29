package ru.softdarom.qrcheck.auth.google.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;
import ru.softdarom.qrcheck.auth.google.service.AuthHandlerExternalService;
import ru.softdarom.qrcheck.auth.google.service.UserSaverService;

@Service
public class UserSaverServiceImpl implements UserSaverService {

    private final AuthHandlerExternalService authHandlerExternalService;

    @Autowired
    UserSaverServiceImpl(AuthHandlerExternalService authHandlerExternalService) {
        this.authHandlerExternalService = authHandlerExternalService;
    }

    @Override
    public void saveOrUpdate(GoogleUserDto userDto, GoogleTokenDto tokenDto) {
        //save to auth handler
        authHandlerExternalService.save(new GoogleCredentialRequest(tokenDto, userDto));
    }
}