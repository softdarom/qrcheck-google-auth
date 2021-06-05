package ru.softdarom.qrcheck.auth.google.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.softdarom.qrcheck.auth.google.model.dto.request.AuthHandlerTokenUserInfoRequest;
import ru.softdarom.qrcheck.auth.google.model.dto.response.AuthHandlerUserResponse;

@FeignClient(name = "auth-handler", url = "${outbound.feign.auth-handler.host}")
public interface AuthHandlerClient {

    @PostMapping("/tokens/info")
    ResponseEntity<AuthHandlerUserResponse> saveOAuth2Info(@RequestHeader("X-ApiKey-Authorization") String apiKey, @RequestBody AuthHandlerTokenUserInfoRequest request);

}