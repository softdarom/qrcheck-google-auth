package ru.softdarom.qrcheck.auth.google.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;

@Configuration
class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DefaultOAuth2UserService googleOAuth2UserService;
    private final OAuth2AuthorizationRequestResolver googleAuthorizationRequestResolver;

    @Autowired
    OAuth2SecurityConfig(DefaultOAuth2UserService googleOAuth2UserService,
                         OAuth2AuthorizationRequestResolver googleAuthorizationRequestResolver) {
        this.googleOAuth2UserService = googleOAuth2UserService;
        this.googleAuthorizationRequestResolver = googleAuthorizationRequestResolver;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .authorizeRequests()
                .antMatchers("/oauth2/**", "/actuator/health", "/actuator/prometheus")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .authorizationEndpoint()
                .authorizationRequestResolver(googleAuthorizationRequestResolver)
                .and()
                .userInfoEndpoint()
                .userService(googleOAuth2UserService);
    }
}