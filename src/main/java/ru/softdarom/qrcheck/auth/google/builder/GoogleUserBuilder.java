package ru.softdarom.qrcheck.auth.google.builder;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.softdarom.qrcheck.auth.google.model.dto.GoogleUserDto;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Slf4j(topic = "GOOGLE-AUTH-BUILDER")
public final class GoogleUserBuilder {

    private final OAuth2User oAuth2User;

    public GoogleUserBuilder(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public GoogleUserDto build() {
        LOGGER.debug("Building a GoogleUserDto by {}", JsonHelper.asJson(oAuth2User));
        return new GoogleUserDto(oAuth2User);
    }
}