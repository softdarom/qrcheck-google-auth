package ru.softdarom.qrcheck.auth.google.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2DeviceRequest;
import ru.softdarom.qrcheck.auth.google.model.request.OAuth2UpdateDeviceRequest;
import ru.softdarom.qrcheck.auth.google.service.UserHandlerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/google/devices")
public class DeviceController {

    private final UserHandlerService userHandlerService;

    @Autowired
    DeviceController(UserHandlerService userHandlerService) {
        this.userHandlerService = userHandlerService;
    }

    @PostMapping
    public ResponseEntity<Void> saveDevice(@Valid @RequestBody OAuth2DeviceRequest request) {
        userHandlerService.saveUserDevice(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateDevice(@Valid @RequestBody OAuth2UpdateDeviceRequest request) {
        userHandlerService.updateUserDevice(request);
        return ResponseEntity.ok().build();
    }
}