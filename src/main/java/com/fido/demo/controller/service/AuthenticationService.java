package com.fido.demo.controller.service;

import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsRequest;
import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsResponse;
import com.fido.demo.controller.pojo.authentication.AuthnRequest;
import com.fido.demo.controller.pojo.authentication.AuthnResponse;
import com.fido.demo.data.entity.RelyingPartyEntity;
import com.fido.demo.data.entity.UserEntity;
import com.fido.demo.data.entity.CredentialEntity;
import com.fido.demo.data.redis.RedisService;
import com.fido.demo.data.repository.CredentialRepository;
import com.fido.demo.data.repository.RPRepository;
import com.fido.demo.data.repository.UserRepository;
import com.fido.demo.util.CredUtils;
import com.fido.demo.util.SessionUtils;
import com.fido.demo.controller.service.pojo.SessionState;

import com.webauthn4j.data.AuthenticationParameters;
import com.webauthn4j.data.RegistrationData;
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
    @Autowired
    private RedisService redisService;

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

    public AuthnResponse authenticate(AuthnRequest request) {
        // fetch the session State: ToDo if session not found, return 404 or 400
        SessionState session = (SessionState) redisService.find(request.getSessionId());

        // validate the challenge & signature sent by client using the registered public-key
        RegistrationData registrationData = credUtils.validateAndGetRegData(request.getServerPublicKeyCredential(), session);





        return null;
    }
}
