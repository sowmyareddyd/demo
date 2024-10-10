package com.fido.demo.controller.service;

import com.fido.demo.controller.pojo.registration.options.RegOptionsRequest;
import com.fido.demo.controller.pojo.registration.options.RegOptionsResponse;
import com.fido.demo.controller.pojo.registration.RegRequest;
import com.fido.demo.controller.pojo.registration.RegResponse;
import com.fido.demo.controller.service.pojo.SessionState;
import com.fido.demo.data.entity.CredentialEntity;
import com.fido.demo.data.entity.RelyingPartyConfigEntity;
import com.fido.demo.data.entity.RelyingPartyEntity;
import com.fido.demo.data.redis.RedisService;
import com.fido.demo.data.repository.RPRepository;
import com.fido.demo.util.CredUtils;
import com.fido.demo.util.Crypto;
import com.fido.demo.util.RpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static com.fido.demo.controller.pojo.registration.options.RegOptionsResponse.PubKeyCredParam;

@Service("registrationService")
public class RegistrationService {

    @Autowired
    private RedisService redisService;

    @Autowired
    RPRepository rpRepository;

    @Autowired
    private RpUtils rpUtils;

    @Autowired
    private CredUtils credUtils;

    @Autowired
    Crypto crypto;

    public RegOptionsResponse getRegOptions(RegOptionsRequest request){
        RegOptionsResponse response = new RegOptionsResponse();
        // construct the response

        //generate the session id
        String sessionId = crypto.generateSecureRandomString(32);
        response.setSessionId(sessionId);

        RelyingPartyEntity relyingPartyEntity = rpRepository.findByRpId(request.getRp().getId());
        if(relyingPartyEntity == null){
            throw new ResourceNotFoundException("RP not found");
        }
        List<RelyingPartyConfigEntity> rpConfigs = relyingPartyEntity.getConfigs();
        List<PubKeyCredParam> pubKeyCredParam = rpUtils.getPubKeyCredParam(relyingPartyEntity.getConfigs());

        response.setPubKeyCredParams(pubKeyCredParam); // pubKeyCredParam

        response.setRp(request.getRp()); // rp

        response.setUser(request.getUser());// user


        response.setAuthenticatorSelection(request.getAuthenticatorSelection());// authenticator selection

        long timeout = rpUtils.getTimeout(relyingPartyEntity.getConfigs());
        response.setTimeout(timeout);// timeout

        String challenge = UUID.randomUUID().toString();// challenge
        String challengeBase64 = Base64.getEncoder().encodeToString(challenge.getBytes());
        SessionState state = SessionState.builder()
                .sessionId(sessionId)
                .rp(request.getRp())
                .challenge(challengeBase64)
                .user(request.getUser())
                .authenticatorSelection(request.getAuthenticatorSelection())
                .timeout(timeout)
                .build();

        redisService.save(sessionId, state); // save the state for subsequent call
        response.setChallenge(challengeBase64);

        response.setExcludeCredentials(new ArrayList<>());// exclude credentials

        String attestation = rpUtils.getAttestation(relyingPartyEntity.getConfigs());
        response.setAttestation(attestation);
        return  response;
    }

    public RegResponse getReg(RegRequest request){
        // fetch the session State
        SessionState session = (SessionState) redisService.find(request.getSessionId());

        CredentialEntity credentialEntity = credUtils.getCredentialEntity(request, session);
        return null;
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