package ru.softdarom.qrcheck.auth.google.builder;


import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;

public final class GoogleUserBuilder {

    private final OAuth2User oAuth2User;

    public GoogleUserBuilder(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public GoogleUserDto build() {
        return new GoogleUserDto(oAuth2User);
    }
}