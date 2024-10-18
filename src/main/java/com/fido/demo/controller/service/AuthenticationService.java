package com.fido.demo.controller.service;

import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsResponse;
import com.fido.demo.controller.pojo.authentication.AuthenticationRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationResponse;
import com.fido.demo.data.entity.RelyingPartyEntity;
import com.fido.demo.data.entity.UserEntity;
import com.fido.demo.data.entity.CredentialEntity;
import com.fido.demo.data.repository.CredentialRepository;
import com.fido.demo.data.repository.RPRepository;
import com.fido.demo.data.repository.UserRepository;
import com.fido.demo.util.CredUtils;
import com.fido.demo.util.SessionUtils;
import com.fido.demo.controller.service.pojo.SessionState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("authenticationService")
public class AuthenticationService {
    @Autowired
    CredentialRepository credentialRepository;

    @Autowired
    RPRepository rpRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    CredUtils credUtils;

    public AuthenticationOptionsResponse getAuthNOptions(AuthenticationOptionsRequest request){
        String rpId = request.getRpId();
        String userId = request.getUserId();
        String userVerification = request.getUserVerification();

        // fetch RP
        RelyingPartyEntity rpEntity = rpRepository.findByRpId(rpId);

        // fetch user
        UserEntity userEntity = userRepository.findByUserId(request.getUserId());
        // save session (challenge, user, rp, sessionId)
        SessionState state = sessionUtils.getAutnSession(request, rpEntity, userEntity);


        // fetch credentials for the user
        List<CredentialEntity> registeredCreds = credentialRepository.findByRpIdAndUserId(rpEntity.getId(), userEntity.getId());

        AuthenticationOptionsResponse response = credUtils.getAuthnOptionsResponse(registeredCreds, state);
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
