package ru.softdarom.qrcheck.auth.google.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.softdarom.qrcheck.auth.google.service.UserHandlerService;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/oauth2")
public class AuthenticationController {

    private final UserHandlerService userHandlerService;

    @Autowired
    AuthenticationController(UserHandlerService userHandlerService) {
        this.userHandlerService = userHandlerService;
    }

    @GetMapping("/users/{email}/exist")
    public ResponseEntity<Void> existUser(@PathVariable("email") @NotEmpty String email) {
        userHandlerService.exist(email);
        return ResponseEntity.ok().build();
    }

}