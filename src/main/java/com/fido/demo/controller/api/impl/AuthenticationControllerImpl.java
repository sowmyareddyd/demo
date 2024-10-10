package com.fido.demo.controller.api.impl;

import com.fido.demo.controller.api.AuthenticationController;
import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsResponse;
import com.fido.demo.controller.pojo.authentication.AuthenticationRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationResponse;
import com.fido.demo.controller.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component("authenticationController")
public class AuthenticationControllerImpl implements AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationOptionsResponse> getRegOptions(AuthenticationOptionsRequest request) {
        AuthenticationOptionsResponse response = authenticationService.getAuthNOptions(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> createRegistration(AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
