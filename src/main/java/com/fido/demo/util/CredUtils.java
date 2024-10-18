package com.fido.demo.util;

import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsResponse;
import com.fido.demo.controller.pojo.registration.RegRequest;
import com.fido.demo.controller.pojo.registration.RegResponse;
import com.fido.demo.controller.service.pojo.SessionState;
import com.fido.demo.data.entity.CredentialEntity;
import com.fido.demo.data.entity.AuthenticatorEntity;
import com.webauthn4j.WebAuthnRegistrationManager;
import com.webauthn4j.data.RegistrationRequest;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.validator.exception.ValidationException;
import org.springframework.stereotype.Component;

import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.data.attestation.AttestationObject;
import com.webauthn4j.data.attestation.authenticator.AAGUID;
import com.webauthn4j.data.RegistrationData;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.converter.exception.DataConversionException;
import com.webauthn4j.data.RegistrationParameters;

import java.math.BigInteger;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.UUID;
import org.springframework.util.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

@Component
public class CredUtils {

    // ToDO : change the cred argument to list
    public AuthenticationOptionsResponse getAuthnOptionsResponse(List<CredentialEntity> registeredCreds, SessionState state){

        // challenge
        String challenge = state.getChallenge();

        // timeout
        long timeout = state.getTimeout();

        //rpId
        String rpId = state.getRp().getId();

        // userVerification : ToDO change it to read from state
        String userVerification = "preferred";

        // sessionid
        String sessionId = state.getSessionId();


        List<Map<String,String>> allowedCreds =registeredCreds.stream().map( (item) -> {
            Map<String, String> entry = new HashMap<String, String>();
            entry.put("type", "public-key");
            entry.put("id", new String(item.getAuthenticatorCredentialId()));
            return entry;
        }).collect(Collectors.toList());


        AuthenticationOptionsResponse response = AuthenticationOptionsResponse.builder()
        .challenge(challenge)
        .timeout(timeout)
        .rpId(rpId)
        .allowedCreds(allowedCreds)
        .userVerification(userVerification)
        //.isUserVerification(userVerification)
        .sessionId(sessionId)
        .build();

        return response;

    }

    public RegRequest getRegistrationResponse(CredentialEntity credEntity){
        // aaguid
        UUID aaguid = credEntity.getAuthenticator().getAaguid();
        // credentialId
        String credentialId = new String(credEntity.getAuthenticatorCredentialId());

        //attestationType
        String attestationType = credEntity.getAttestationFormat();

        // authenticatorTransports
        String authenticatorTransports = StringUtils.collectionToDelimitedString(credEntity.getAuthenticator().getTransports(), ",");

        //userVerified: ToDo: update this code
        boolean userVerified = true;

        //residentKey present or not: ToDO : update this code
        boolean rk = true;

        RegRequest ret = RegRequest.builder()
                .aaguid(aaguid.toString())
                .credentialId(credentialId)
                .attestationType(attestationType)
                .authenticatorTransports(new ArrayList<String>(){{
                    add(authenticatorTransports);
                }})
                .userVerified(userVerified)
                .rk(rk)
                .build();

        return ret;

    }
    public CredentialEntity getCredentialEntity(RegRequest request, SessionState session, RegistrationData registrationData) {
        // id, user_id, rp_id, public_key(UUID), sign_count, transports, attestation_format, authenticator_credential_id
        //id is auto generated

        // rp_id : note this is Db id
        BigInteger rpId = session.getRpDbId();

        // user_id
        BigInteger userId = session.getUserDbId();

        // public_key
        byte[] publicKey = this.getPubKey(request).getBytes();

        // signCount
        long signCount = this.getSignCount(registrationData);

        // ToDo : for now only attestation_none cases are handled => below hard coded value
        String attestationFormat = "none";

        // authenticator_credential_id
        byte[] authneticatorCredentialId = registrationData.getAttestationObject().getAuthenticatorData().getAttestedCredentialData().getCredentialId(); //ToDo : avoid byte to string conversion ?

        CredentialEntity credentialEntity = CredentialEntity.builder()
                .rpId(rpId)                                                      /* Column : rp_id */
                .userId(userId)                                                  /* Column : user_id */
                .publicKey(publicKey)                                            /* Column : public_key */
                .sign_count(signCount)                                           /* Column : sign_count */
                .attestationFormat(attestationFormat)                            /* Column : attestation_format */
                .authenticatorCredentialId(authneticatorCredentialId)            /* Column : authenticator_credential_id */
                .build();

        return  credentialEntity;
    }


    public AuthenticatorEntity getAuthenticatorEntity(RegRequest request, RegistrationData registrationData){
        // transports
        List<String> transports = registrationData.getTransports().stream().map(transport -> transport.getValue()).collect(Collectors.toList()); // ToDo : handle multiple transports
        AAGUID aauid = registrationData.getAttestationObject().getAuthenticatorData().getAttestedCredentialData().getAaguid();
        AuthenticatorEntity authenticatorEntity = AuthenticatorEntity.builder()
                .aaguid(aauid.getValue())
                .transports(transports)
                .build();

        return authenticatorEntity;


    }


    private long getSignCount(RegistrationData registrationData) {
        long signCount = registrationData.getAttestationObject().getAuthenticatorData().getSignCount(); //ToDo : use null checks, regData is returned by WebAuthn4J lib so might not need null checks
        return  signCount;
    }
    private String getPubKey(RegRequest request) {
        RegRequest.ServerPublicKeyCredential serverPublicKeyCredential = request.getServerPublicKeyCredential();
        String publicKey = serverPublicKeyCredential.getResponse().getPublicKey();
        return publicKey;
    }

    private String getTransports(RegRequest request){
        RegRequest.ServerPublicKeyCredential serverPublicKeyCredential = request.getServerPublicKeyCredential();
        List<String> transports = serverPublicKeyCredential.getResponse().getTransports();
        return String.join(",", transports);
    }


    public RegistrationData validateAndGetRegData(RegRequest request, SessionState sessionState){

        WebAuthnRegistrationManager webAuthnManager = WebAuthnRegistrationManager.createNonStrictWebAuthnRegistrationManager();
        RegRequest.Response clientResponse = request.getServerPublicKeyCredential().getResponse();

        // client properties
        byte[] attestationObject = Base64.getUrlDecoder().decode(clientResponse.getAttestationObject()); /* set attestationObject */
        byte[] clientDataJSON = Base64.getDecoder().decode(clientResponse.getClientDataJSON()); /* set clientDataJSON */;
        String clientExtensionJSON = null;  /* set clientExtensionJSON */;
        Set<String> transports = new HashSet<String>(clientResponse.getTransports()); /* set transports */;

        // Server properties
        Origin origin = Origin.create(sessionState.getRp().getOrigin()) /* set origin */;
        String rpId = sessionState.getRp().getId() /* set rpId */;
        Challenge challenge = new DefaultChallenge(sessionState.getChallenge()); /* set challenge */;
        byte[] tokenBindingId = null /* set tokenBindingId */;
        ServerProperty serverProperty = new ServerProperty(origin, rpId, challenge, tokenBindingId);

        // expectations
        boolean userVerificationRequired = false;
        boolean userPresenceRequired = true;

        RegistrationRequest registrationRequest = new RegistrationRequest(attestationObject, clientDataJSON, clientExtensionJSON, transports);
        RegistrationParameters registrationParameters = new RegistrationParameters(serverProperty, userVerificationRequired, userPresenceRequired);

        RegistrationData registrationData;
        try{
            registrationData = webAuthnManager.parse(registrationRequest);
        }
        catch (DataConversionException e){
            // If you would like to handle WebAuthn data structure parse error, please catch DataConversionException
            throw e;
        }

        try{
            webAuthnManager.validate(registrationData, registrationParameters);
        }
        catch (ValidationException e){
            // If you would like to handle WebAuthn data validation error, please catch ValidationException
            throw e;
        }

        return registrationData;

    }
}
