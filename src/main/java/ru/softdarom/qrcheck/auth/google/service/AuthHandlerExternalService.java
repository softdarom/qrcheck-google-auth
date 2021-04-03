package ru.softdarom.qrcheck.auth.google.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;

@FeignClient(name = "auth-handler", url = "${outbound.feign.auth-handler}")
public interface AuthHandlerExternalService {

    @PostMapping("/users/save")
    void save(GoogleCredentialRequest request);

    @GetMapping("/users/{email}")
    void exist(@PathVariable("email") String email);

}