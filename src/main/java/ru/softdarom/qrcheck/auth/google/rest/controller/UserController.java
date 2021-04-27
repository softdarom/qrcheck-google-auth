package ru.softdarom.qrcheck.auth.google.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.softdarom.qrcheck.auth.google.service.UserHandlerService;

@RestController
@RequestMapping("/google/users")
public class UserController {

    private final UserHandlerService userHandlerService;

    @Autowired
    UserController(UserHandlerService userHandlerService) {
        this.userHandlerService = userHandlerService;
    }

    @GetMapping
    public ResponseEntity<Long> existUser(@RequestParam("email") String email) {
        return ResponseEntity.ok(userHandlerService.exist(email));
    }

}