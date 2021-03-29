package ru.softdarom.qrcheck.auth.google.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@EqualsAndHashCode
@ToString
public class GoogleUserDto {

    private final String firstName;
    private final String secondName;
    private final String avatar;
    private final String email;

    public GoogleUserDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        this.firstName = (String) attributes.get("given_name");
        this.secondName = (String) attributes.get("family_name");
        this.avatar = (String) attributes.get("picture");
        this.email = (String) attributes.get("email");
    }
}