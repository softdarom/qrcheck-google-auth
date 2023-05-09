package ru.softdarom.qrcheck.auth.google.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.softdarom.qrcheck.auth.google.model.dto.response.BaseResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleAccessTokenResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.GoogleTokenInfoResponse;
import ru.softdarom.qrcheck.auth.google.model.dto.response.MobileUserInfoResponse;
import ru.softdarom.qrcheck.auth.google.service.OAuth2Service;

@Tag(name = "oAuth2", description = "Контроллер управления oAuth 2.0 token'ами")
@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    private final OAuth2Service auth2Service;

    @Autowired
    OAuth2Controller(OAuth2Service auth2Service) {
        this.auth2Service = auth2Service;
    }

    @Operation(summary = "Endpoint на который идет redirect после успешной аутентификации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token'ы получены и сохранены",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = MobileUserInfoResponse.class))

                            }
                    ),
                    @ApiResponse(
                            responseCode = "302",
                            description = "Неизвестная ошибка. Редирект на /oauth2/failure",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @GetMapping("/success")
    public MobileUserInfoResponse success(Authentication authentication) {
        return new MobileUserInfoResponse(auth2Service.saveOAuthInfo(authentication));
    }

    @Operation(summary = "Endpoint на который идет redirect после неуспешной аутентификации")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "500",
                            description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @GetMapping("/failure")
    public ResponseEntity<BaseResponse> failure() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("Error Google Authentication!"));
    }

    @Operation(summary = "Получение информации о access token")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация получена",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = GoogleTokenInfoResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @GetMapping("/tokens/info")
    public GoogleTokenInfoResponse getTokenInfo(@RequestParam String accessToken) {
        return auth2Service.tokenInfo(accessToken);
    }

    @Operation(summary = "Обновление access token по refresh token")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Access token обновлен",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = GoogleAccessTokenResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Неизвестная ошибка",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))
                            }
                    )
            }
    )
    @PostMapping("/tokens/refresh")
    public GoogleAccessTokenResponse refresh(@RequestParam String refreshToken) {
        return auth2Service.refresh(refreshToken);
    }
}