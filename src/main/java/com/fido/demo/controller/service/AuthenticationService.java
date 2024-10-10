package com.fido.demo.controller.service;

import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsResponse;
import com.fido.demo.controller.pojo.authentication.AuthenticationRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationResponse;
import com.fido.demo.data.repository.CredentialRepository;
import com.fido.demo.data.repository.RPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authenticationService")
public class AuthenticationService {
    @Autowired
    CredentialRepository credentialRepository;

    @Autowired
    RPRepository rpRepository;


    public AuthenticationOptionsResponse getAuthNOptions(AuthenticationOptionsRequest request){
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
