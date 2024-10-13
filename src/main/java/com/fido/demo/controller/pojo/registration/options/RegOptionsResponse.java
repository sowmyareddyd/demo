//
//package com.fido.demo.controller.pojo.registration.options;
//
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fido.demo.controller.pojo.registration.RP;
//import com.fido.demo.controller.pojo.registration.User;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data // for getters and setters
//@NoArgsConstructor
//public class RegOptionsResponse {
//
//
//    @JsonProperty("rp")
//    private RP rp;
//
//    @JsonProperty("user")
//    private User user;
//
//
//
//    @JsonProperty("authenticatorSelection")
//    private AuthenticatorSelection authenticatorSelection;
//
//    @JsonProperty("attestation")
//    private String attestation;
//
//
//
//    public ServerResponse getServerResponse() {
//        return serverResponse;
//    }
//
//    public void setServerResponse(ServerResponse serverResponse) {
//        this.serverResponse = serverResponse;
//    }
//
//    public RP getRp() {
//        return rp;
//    }
//
//    public void setRp(RP rp) {
//        this.rp = rp;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getChallenge() {
//        return challenge;
//    }
//
//    public void setChallenge(String challenge) {
//        this.challenge = challenge;
//    }
//
//    public List<PubKeyCredParam> getPubKeyCredParams() {
//        return pubKeyCredParams;
//    }
//
//    public void setPubKeyCredParams(List<PubKeyCredParam> pubKeyCredParams) {
//        this.pubKeyCredParams = pubKeyCredParams;
//    }
//
//    public long getTimeout() {
//        return timeout;
//    }
//
//    public void setTimeout(long timeout) {
//        this.timeout = timeout;
//    }
//
//    public List<Object> getExcludeCredentials() {
//        return excludeCredentials;
//    }
//
//    public void setExcludeCredentials(List<Object> excludeCredentials) {
//        this.excludeCredentials = excludeCredentials;
//    }
//
//    public AuthenticatorSelection getAuthenticatorSelection() {
//        return authenticatorSelection;
//    }
//
//    public void setAuthenticatorSelection(AuthenticatorSelection authenticatorSelection) {
//        this.authenticatorSelection = authenticatorSelection;
//    }
//
//    public String getAttestation() {
//        return attestation;
//    }
//
//    public void setAttestation(String attestation) {
//        this.attestation = attestation;
//    }
//
//    public String getSessionId() {
//        return sessionId;
//    }
//
//    public void setSessionId(String sessionId) {
//        this.sessionId = sessionId;
//    }
//
//    public Extensions getExtensions() {
//        return extensions;
//    }
//
//    public void setExtensions(Extensions extensions) {
//        this.extensions = extensions;
//    }
//
//
//}
