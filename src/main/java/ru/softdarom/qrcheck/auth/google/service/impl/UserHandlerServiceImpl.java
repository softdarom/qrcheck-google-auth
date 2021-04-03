package ru.softdarom.qrcheck.auth.google.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;
import ru.softdarom.qrcheck.auth.google.service.AuthHandlerExternalService;
import ru.softdarom.qrcheck.auth.google.service.UserHandlerService;

@Service
public class UserHandlerServiceImpl implements UserHandlerService {

    private final AuthHandlerExternalService authHandlerExternalService;

    @Autowired
    UserHandlerServiceImpl(AuthHandlerExternalService authHandlerExternalService) {
        this.authHandlerExternalService = authHandlerExternalService;
    }

    @Override
    public void saveOrUpdate(GoogleUserDto userDto, GoogleTokenDto tokenDto) {
        Assert.notNull(userDto, "The 'userDto' must not null!");
        Assert.notNull(tokenDto, "The 'tokenDto' must not null!");
        authHandlerExternalService.save(new GoogleCredentialRequest(tokenDto, userDto));
    }

    @Override
    public void exist(String email) {
        Assert.notNull(email, "The 'email' must not be null!");
        Assert.isTrue(!email.isEmpty(), "The 'email' must not be empty!");
        authHandlerExternalService.exist(email);
    }
}