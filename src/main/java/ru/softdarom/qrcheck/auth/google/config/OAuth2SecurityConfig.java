package ru.softdarom.qrcheck.auth.google.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2AuthorizationRequestResolver googleAuthorizationRequestResolver;

    @Autowired
    OAuth2SecurityConfig(OAuth2AuthorizationRequestResolver googleAuthorizationRequestResolver) {
        this.googleAuthorizationRequestResolver = googleAuthorizationRequestResolver;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .authorizeRequests(request -> request
                        .antMatchers(
                                "/oauth2/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handlerConfigurer -> handlerConfigurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login()
                        .defaultSuccessUrl("/oauth2/success")
                        .failureUrl("/oauth2/failure")
                    .authorizationEndpoint()
                        .authorizationRequestResolver(googleAuthorizationRequestResolver)
                    .and()
                        .redirectionEndpoint()
                            .baseUri("/oauth2/callback/**");
        // @formatter:on
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-api/**"
                )
                .antMatchers(
                        "/actuator/health/**",
                        "/actuator/prometheus/**",
                        "/oauth2/tokens/**"
                );
    }
}