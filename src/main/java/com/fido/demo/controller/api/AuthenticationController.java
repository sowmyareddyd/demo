package com.fido.demo.controller.api;

import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsResponse;
import com.fido.demo.controller.pojo.authentication.AuthenticationRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fido2")
public interface AuthenticationController {
    
    // Get challenge options for registration
    @PostMapping("/authentication/options")
    public ResponseEntity<AuthenticationOptionsResponse> getRegOptions(AuthenticationOptionsRequest request);

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> createRegistration(AuthenticationRequest request);
}