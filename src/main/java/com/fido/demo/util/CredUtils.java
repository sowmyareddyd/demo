package com.fido.demo.util;

import com.fido.demo.controller.pojo.authentication.AuthenticationOptionsResponse;
import com.fido.demo.controller.pojo.registration.RegRequest;
import com.fido.demo.controller.pojo.registration.RegResponse;
import com.fido.demo.controller.pojo.registration.ServerPublicKeyCredential;
import com.fido.demo.controller.service.pojo.SessionState;
import com.fido.demo.data.entity.AuthenticatorConfigEntity;
import com.fido.demo.data.entity.CredentialConfigEntity;
import com.fido.demo.data.entity.CredentialEntity;
import com.fido.demo.data.entity.AuthenticatorEntity;
import com.fido.demo.data.repository.CredentialRepository;
import com.webauthn4j.WebAuthnRegistrationManager;
import com.webauthn4j.authenticator.Authenticator;
import com.webauthn4j.authenticator.AuthenticatorImpl;
import com.webauthn4j.data.*;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.validator.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.data.attestation.AttestationObject;
import com.webauthn4j.data.attestation.authenticator.AAGUID;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.converter.exception.DataConversionException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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

    @Autowired
    JSONUtils jsonUtils;

    @Autowired
    CredentialRepository credentialRepository;


    /* --------------------------------- Registration Uitls (start)--------------------------------*/

    // credentials are persisted, build "registration" response
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

    public RegistrationData validateAndGetRegData(ServerPublicKeyCredential publicKeyCredential, SessionState sessionState){

        WebAuthnRegistrationManager webAuthnManager = WebAuthnRegistrationManager.createNonStrictWebAuthnRegistrationManager();
        ServerPublicKeyCredential.Response clientResponse = publicKeyCredential.getResponse();

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

    /* --------------------------------- Registration Uitls (end) --------------------------------*/


    /* --------------------------------- Authentication Uitls (start)  --------------------------------*/
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

    public RegistrationData validateAndGetAuthnData(ServerPublicKeyCredential publicKeyCredential, SessionState sessionState){

        WebAuthnRegistrationManager webAuthnManager = WebAuthnRegistrationManager.createNonStrictWebAuthnRegistrationManager();
        ServerPublicKeyCredential.Response clientResponse = publicKeyCredential.getResponse();

        // client properties
        byte[] credentialId = publicKeyCredential.getId().getBytes();
        byte[] userHandle = publicKeyCredential.getResponse().getUserHandle().getBytes();

        byte[] authenticatorData = Base64.getUrlDecoder().decode(clientResponse.getAuthenticatorData()); /* set attestationObject */
        byte[] clientDataJSON = Base64.getDecoder().decode(clientResponse.getClientDataJSON()); /* set clientDataJSON */;
        String clientExtensionJSON = null;  /* set clientExtensionJSON */;
        byte[] signature = clientResponse.getSignature().getBytes();
        Set<String> transports = new HashSet<String>(clientResponse.getTransports()); /* set transports */;

        // Server properties
        Origin origin = Origin.create(sessionState.getRp().getOrigin()) /* set origin */;
        String rpId = sessionState.getRp().getId() /* set rpId */;
        Challenge challenge = new DefaultChallenge(sessionState.getChallenge()); /* set challenge */;
        byte[] tokenBindingId = null /* set tokenBindingId */;
        ServerProperty serverProperty = new ServerProperty(origin, rpId, challenge, tokenBindingId);

        // expectations
        List<byte[]> allowCredentials = null;
        boolean userVerificationRequired = true;
        boolean userPresenceRequired = true;

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                credentialId, /* id of the credential*/
                userHandle,
                authenticatorData,
                clientDataJSON,
                clientExtensionJSON,
                signature
        );

        List<CredentialEntity> savedCred = credentialRepository.findByRpIdAndUserId(sessionState.getRpDbId(), sessionState.getUserDbId());
        // filter if the incoming cred id is present
        CredentialEntity credEntity = savedCred.stream().filter(item -> {
            String id = new String(item.getAuthenticatorCredentialId());
            return id.compareTo(publicKeyCredential.getId()) == 0;
        }).findFirst().orElseGet(null);

        if(credEntity == null){
            throw new ResourceNotFoundException("Credential not found");
        }


        Authenticator authenticator = this.getWebAuthn4jAuthenticator(credEntity);
        AuthenticationParameters authnParams = new AuthenticationParameters(
                serverProperty,
                authenticator,
                allowCredentials,
                userVerificationRequired,
                userPresenceRequired
        );
        RegistrationParameters registrationParameters = new RegistrationParameters(serverProperty, userVerificationRequired, userPresenceRequired);


        return null;

    }
    private Authenticator getWebAuthn4jAuthenticator(CredentialEntity credentialEntity){
        AttestationObject attestationObject = null;

        CredentialConfigEntity config = credentialEntity.getConfigs().get(0);
        String configString = config.getSettingValue();
        attestationObject = (AttestationObject) jsonUtils.toObject(configString, AttestationObject.class);

        long signCount = attestationObject.getAuthenticatorData().getSignCount();
        AttestedCredentialData attestedCredentialData =attestationObject.getAuthenticatorData().getAttestedCredentialData();
        AttestationStatement statement = attestationObject.getAttestationStatement();
        Authenticator authenticator = new AuthenticatorImpl(attestedCredentialData, statement, signCount);
        return authenticator;

    }

    /* --------------------------------- Authentication Uitls (end)  --------------------------------*/



    /* --------------------------------- Common Uitls (start) --------------------------------*/

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
        byte[] authneticatorCredentialId = request.getServerPublicKeyCredential().getId().getBytes();

        List<CredentialConfigEntity> configs = this.getCredentialConfigs(session, registrationData);

        CredentialEntity credentialEntity = CredentialEntity.builder()
                .rpId(rpId)                                                      /* Column : rp_id */
                .userId(userId)                                                  /* Column : user_id */
                .publicKey(publicKey)                                            /* Column : public_key */
                .sign_count(signCount)                                           /* Column : sign_count */
                .attestationFormat(attestationFormat)                            /* Column : attestation_format */
                .authenticatorCredentialId(authneticatorCredentialId)            /* Column : authenticator_credential_id */
                .configs(configs)                                                /* Child : credential configs */
                .build();

        return  credentialEntity;
    }

    private List<CredentialConfigEntity> getCredentialConfigs(SessionState session, RegistrationData registrationData){

        AttestationObject attestationObject = registrationData.getAttestationObject();
        AttestedCredentialData attestedCredentialData = attestationObject.getAuthenticatorData().getAttestedCredentialData();
        String attestedCredJSON = jsonUtils.toJSONString(attestedCredentialData);

        CredentialConfigEntity configEntity = CredentialConfigEntity.builder()
                .settingKey("attestation_data")
                .settingValue(attestedCredJSON)
                .build();
        List<CredentialConfigEntity> ret = new ArrayList<>();
        ret.add(configEntity);
        return ret;
    }

    private long getSignCount(RegistrationData registrationData) {
        long signCount = registrationData.getAttestationObject().getAuthenticatorData().getSignCount(); //ToDo : use null checks, regData is returned by WebAuthn4J lib so might not need null checks
        return  signCount;
    }

    private String getPubKey(RegRequest request) {
        ServerPublicKeyCredential serverPublicKeyCredential = request.getServerPublicKeyCredential();
        String publicKey = serverPublicKeyCredential.getResponse().getPublicKey();
        return publicKey;
    }

    private String getTransports(RegRequest request){
        ServerPublicKeyCredential serverPublicKeyCredential = request.getServerPublicKeyCredential();
        List<String> transports = serverPublicKeyCredential.getResponse().getTransports();
        return String.join(",", transports);
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

    /* --------------------------------- Common Uitls --------------------------------*/
}
