package ru.softdarom.qrcheck.auth.google.service.impl.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.auth.google.builder.GoogleTokenBuilder;
import ru.softdarom.qrcheck.auth.google.builder.GoogleUserBuilder;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleTokenDto;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.service.UserSaverService;

@Service
public class GoogleOAuth2UserService extends DefaultOAuth2UserService {

    private final UserSaverService userSaverService;

    @Autowired
    GoogleOAuth2UserService(UserSaverService userSaverService) {
        this.userSaverService = userSaverService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        var oAuth2User = super.loadUser(oAuth2UserRequest);
        userSaverService.saveOrUpdate(getGoogleOAuth2User(oAuth2User), getGoogleToken(oAuth2UserRequest));
        return oAuth2User;
    }

    private GoogleUserDto getGoogleOAuth2User(OAuth2User oAuth2User) {
        return new GoogleUserBuilder(oAuth2User).build();
    }

    private GoogleTokenDto getGoogleToken(OAuth2UserRequest oAuth2UserRequest) {
        return new GoogleTokenBuilder(oAuth2UserRequest).build();
    }

}
