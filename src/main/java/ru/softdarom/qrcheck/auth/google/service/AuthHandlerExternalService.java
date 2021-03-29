package ru.softdarom.qrcheck.auth.google.service;

import feign.Headers;
import feign.RequestLine;
import ru.softdarom.qrcheck.auth.google.model.request.GoogleCredentialRequest;

public interface AuthHandlerExternalService {

    @RequestLine("POST /save")
    @Headers("Content-Type: application/json")
    void save(GoogleCredentialRequest request);

}