package ru.softdarom.qrcheck.auth.google.builder;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;
import ru.softdarom.qrcheck.auth.google.model.dto.UserDto;
import ru.softdarom.qrcheck.auth.google.util.JsonHelper;

@Slf4j(topic = "BUILDER")
public final class UserBuilder {

    private final OAuth2User oAuth2User;

    public UserBuilder(OAuth2User oAuth2User) {
        Assert.notNull(oAuth2User, "The 'oAuth2User' must not be null!");
        this.oAuth2User = oAuth2User;
    }

    public UserDto build() {
        LOGGER.debug("Building a UserDto by {}", JsonHelper.asJson(oAuth2User));
        var attributes = oAuth2User.getAttributes();
        var firstName = (String) attributes.get("given_name");
        var secondName = (String) attributes.get("family_name");
        var email = (String) attributes.get("email");
        var picture = (String) attributes.get("picture");
        return new UserDto(firstName, secondName, email, picture);
    }
}