package com.fido.demo.controller.service;

import com.fido.demo.controller.pojo.registration.options.AuthenticatorSelection;
import com.fido.demo.controller.pojo.registration.options.RegOptions;
import com.fido.demo.controller.pojo.registration.RegRequest;
import com.fido.demo.controller.service.pojo.SessionState;
import com.fido.demo.data.entity.AuthenticatorEntity;
import com.fido.demo.data.entity.CredentialEntity;
import com.fido.demo.data.entity.RelyingPartyEntity;
import com.fido.demo.data.redis.RedisService;
import com.fido.demo.data.repository.RPRepository;
import com.fido.demo.data.repository.CredentialRepository;
import com.fido.demo.util.CredUtils;
import com.fido.demo.util.CryptoUtil;
import com.fido.demo.util.RpUtils;
import com.webauthn4j.data.RegistrationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static com.fido.demo.controller.pojo.registration.options.RegOptions.PubKeyCredParam;

@Service("registrationService")
public class RegistrationService {

    @Autowired
    private RedisService redisService;

    @Autowired
    RPRepository rpRepository;

    @Autowired
    CredentialRepository credRepository;

    @Autowired
    private RpUtils rpUtils;

    @Autowired
    private CredUtils credUtils;

    @Autowired
    CryptoUtil cryptoUtil;

    public RegOptions getRegOptions(RegOptions request){
        //session_id : secure random string
        String sessionId = cryptoUtil.generateSecureRandomString(32);

        RelyingPartyEntity relyingPartyEntity = rpRepository.findByRpId(request.getRp().getId());
        if(relyingPartyEntity == null){
            throw new ResourceNotFoundException("RP not found");
        }

        AuthenticatorSelection authenticatorSelection = rpUtils.getAuthenticatorSelection(relyingPartyEntity.getConfigs());
        String attestation = rpUtils.getAttestation(relyingPartyEntity.getConfigs());
        List<PubKeyCredParam> pubKeyCredParam = rpUtils.getPubKeyCredParam(relyingPartyEntity.getConfigs());
        long timeout = rpUtils.getTimeout(relyingPartyEntity.getConfigs());

        String challenge = cryptoUtil.generateSecureRandomString(32);// challenge
        String challengeBase64 = Base64.getEncoder().encodeToString(challenge.getBytes());
        SessionState state = SessionState.builder() // ToDo : instead of saving the incoming data without validation, validate and persist
                .sessionId(sessionId)
                .rp(request.getRp())
                .rpDbId(relyingPartyEntity.getId())
                .challenge(challengeBase64)
                .user(request.getUser())
                .authenticatorSelection(request.getAuthenticatorSelection())
                .timeout(timeout)
                .build();

        redisService.save(sessionId, state); // save the state for subsequent call

        RegOptions response = RegOptions.builder() // build the response
                .rp(request.getRp())                                                   /* relying party*/
                .user(request.getUser())                                               /* user */
                .authenticatorSelection(authenticatorSelection)                        /* authenticator selection */
                .attestation(attestation)                                              /* attestation */
                .challenge(challengeBase64)                                            /* challenge */
                .pubKeyCredParams(pubKeyCredParam)                                     /* pubKeyCredParams */
                .timeout(timeout)                                                      /* timeout */
                .excludeCredentials(new ArrayList<>())                                 /* excludeCredentials : ToDo - parse from Db and set the value */
                .sessionId(sessionId)                                                  /* sessionId */
                .build();

        return  response;
    }

    public RegRequest getReg(RegRequest request){
        // fetch the session State
        SessionState session = (SessionState) redisService.find(request.getSessionId());


        // validate session and extract registrationData
        RegistrationData registrationData = credUtils.validateAndGetRegData(request, session);

        CredentialEntity credentialEntity = credUtils.getCredentialEntity(request, session, registrationData);
        AuthenticatorEntity authenticatorEntity = credUtils.getAuthenticatorEntity(request, registrationData);
        credentialEntity.setAuthenticator(authenticatorEntity);

        // persist the credentials
        CredentialEntity savedCreds = credRepository.save(credentialEntity);


        // construct the response and return
        RegRequest response = credUtils.getRegistrationResponse(savedCreds);

        return response;
    }

}
// Sample incoming request
/*
{
  "rp" : {
    "name" : "Test RP",
    "icon" : null,
    "id" : "localhost"
  },
  "user" : {
    "name" : "TestUser",
    "icon" : null,
    "id" : "65fUCTlqPlOSk22tkrkJ2m8I2MEhpF4fCI_pdosMAzk",
    "displayName" : "Test Display Name"
  },
  "authenticatorSelection": {
    "requireResidentKey": true,
    "userVerification": "preferred",
    "authenticatorAttachment": "platform"
  },
  "attestation": "none",
  "credProtect" : null
}
*/


// Sample response
/*
{
  "serverResponse" : {
    "description" : null,
    "internalError" : "SUCCESS",
    "internalErrorCode" : 0,
    "internalErrorCodeDescription" : null
  },
  "rp" : {
    "name" : "example1",
    "icon" : null,
    "id" : "localhost"
  },
  "user" : {
    "name" : "TestUser",
    "icon" : null,
    "id" : "65fUCTlqPlOSk22tkrkJ2m8I2MEhpF4fCI_pdosMAzk",
    "displayName" : "Test Display Name"
  },
  "challenge" : "4uxOYNYRZ0a-WPuh24Uki0kNrM5_ioDPVNgaCfy2szLzc0QaVamxa66Y7v8bAyxUGiqU7O8mXP36R_oyWaVDpg",
  "pubKeyCredParams" : [ {
    "type" : "public-key",
    "alg" : -65535
  }, {
    "type" : "public-key",
    "alg" : -257
  }, {
    "type" : "public-key",
    "alg" : -258
  }, {
    "type" : "public-key",
    "alg" : -259
  }, {
    "type" : "public-key",
    "alg" : -37
  }, {
    "type" : "public-key",
    "alg" : -38
  }, {
    "type" : "public-key",
    "alg" : -39
  }, {
    "type" : "public-key",
    "alg" : -7
  }, {
    "type" : "public-key",
    "alg" : -35
  }, {
    "type" : "public-key",
    "alg" : -36
  }, {
    "type" : "public-key",
    "alg" : -8
  }, {
    "type" : "public-key",
    "alg" : -43
  } ],
  "timeout" : 180000,
  "excludeCredentials" : [ ],
  "authenticatorSelection" : {
    "authenticatorAttachment" : "platform",
    "requireResidentKey" : true,
    "userVerification" : "preferred"
  },
  "attestation" : "none",
  "sessionId" : "d9adf5d0-e819-42de-ac35-defa241f1b6c",
  "extensions" : {
    "credProps" : true
  }
}
*/