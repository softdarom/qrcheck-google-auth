package ru.softdarom.qrcheck.auth.google.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2DeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2UpdateDeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.response.GoogleUserResponse;

@FeignClient(name = "auth-handler", url = "${outbound.feign.auth-handler.host}")
public interface AuthHandlerExternalService {

    @PostMapping("/users")
    void saveUser(GoogleCredentialRequest request);

    @GetMapping("/users")
    GoogleUserResponse getUser(@RequestParam("email") String email);

    @PostMapping("/devices")
    void saveDevice(OAuth2DeviceRequest request);

    @PutMapping("/devices")
    void updateDevice(OAuth2UpdateDeviceRequest request);


}